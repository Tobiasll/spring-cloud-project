package com.tobias.service.provider.web.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tobias.api.UserService;
import com.tobias.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class UserController implements UserService {

  
  private static final Random RANDOM = new Random(47);

  private final UserService userService;

  @Value("${user.name}")
  private String userName;

  @Value("${user.host}")
  private String userHost;

  @Value("${user.area}")
  private String userArea;

  @Autowired
  public UserController(@Qualifier("inMemoryUserServiceImpl") UserService userService) {
    this.userService = userService;
  }


  @GetMapping("/user/getUserInfo")
  public String getUserInfo() {
    return userName + " : " + userHost + " ; " + userArea;
  }

  @Override
  public boolean saveUser(@RequestBody User user) {
    return userService.saveUser(user);
  }


  @HystrixCommand(
      commandProperties = {
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
      },
      fallbackMethod = "fallBackFromFindAll"
  )
  @Override
  public List<User> findAll() {
    return userService.findAll();
  }


  @HystrixCommand(
      commandProperties = {
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
      },
      fallbackMethod = "fallBackFromFindAll"
  )
  @GetMapping("getUser")
  public List<User> getUser() throws InterruptedException {
    long executeTime = RANDOM.nextInt(200);
    Thread.sleep(executeTime);

    // 通过休眠来模拟执行时间
    System.out.println("Execute Time : " + executeTime + " ms");

    return userService.findAll();
  }

  public List<User> fallBackFromFindAll() {
    System.out.println("fallBackFromFindAll");
    return new ArrayList<>();
  }

}
