package com.tobias.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tobias.api.UserService;
import com.tobias.client.stream.UserMessage;
import com.tobias.domain.User;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class UserServiceClientController implements UserService {

  @Value("${user.name}")
  private String userName;

  @Value("${user.host}")
  private String userHost;

  @Value("${user.area}")
  private String userArea;

  private final UserService userService;

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final UserMessage userMessage;
  private final ObjectMapper objectMapper;

  @Autowired
  public UserServiceClientController(KafkaTemplate<String, Object> kafkaTemplate,
      UserService userService, UserMessage userMessage, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.userMessage = userMessage;
    this.userService = userService;
    this.objectMapper = objectMapper;
  }


  @GetMapping("/user/getUserInfo")
  public String getUserInfo() {
    return "user-server-client : " + userName + " : " + userHost + " : " + userArea;
  }

  @PostMapping("/user/saveByKafka")
  public boolean saveByKafka(@RequestBody User user) {
    return kafkaTemplate.send("users", 0, user).isDone();
  }

  @PostMapping("/user/saveByRabbitMQ")
  public boolean savaByRabbitMQ(@RequestBody User user) throws JsonProcessingException {
    MessageChannel messageChannel = userMessage.output();
    return messageChannel.send(new GenericMessage<>(objectMapper.writeValueAsString(user)));
  }


  @Override
  public boolean saveUser(@RequestBody User user) {
    return userService.saveUser(user);
  }

  @HystrixCommand(
      commandProperties = {
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")},
      fallbackMethod = "fallbackForFindAll"
  )
  @Override
  public List<User> findAll() {
    return userService.findAll();
  }

  public List<User> fallbackForFindAll() {
    System.out.println(" fallbackForFindAll from client");
    return Collections.emptyList();
  }

}
