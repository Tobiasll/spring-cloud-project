package com.tobias.service.provider.provider.service;


import static com.tobias.service.provider.stream.UserMessage.INPUT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobias.api.UserService;
import com.tobias.domain.User;
import com.tobias.service.provider.stream.UserMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Service;

/**
 * 用户 消息服务
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 0.0.1
 */
@Service
public class UserMessageService {

    private final UserMessage userMessage;

    private final UserService userService;

    private final ObjectMapper objectMapper;

    @Autowired
    public UserMessageService(UserMessage userMessage,
        @Qualifier("inMemoryUserServiceImpl") UserService userService, ObjectMapper objectMapper) {
        this.userMessage = userMessage;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }


    @ServiceActivator(inputChannel = INPUT)
    public void listen(byte[] data) throws IOException {
        System.out.println("Subscribe by @ServiceActivator");
        saveUser(data);
    }

    @StreamListener(INPUT)
    public void onMessage(byte[] data) throws IOException {
        System.out.println("Subscribe by @StreamListener");
        saveUser(data);
    }

    private void saveUser(String data) throws IOException {
        User user = objectMapper.readValue(data, User.class);
        userService.saveUser(user);
    }

    private void saveUser(byte[] data) {
        // message body 是字节流 byte[]
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            User user = (User) objectInputStream.readObject();
            userService.saveUser(user);
            System.out.println(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {

        SubscribableChannel subscribableChannel = userMessage.input();
        subscribableChannel.subscribe(message -> {
            System.out.println("Subscribe by SubscribableChannel");
            String contentType = message.getHeaders().get("contentType", String.class);
            if ("text/plain".equals(contentType)) {
                try {
                    saveUser((String) message.getPayload());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // message body 是字节流 byte[]
                byte[] body = (byte[]) message.getPayload();
                saveUser(body);
            }
        });
    }

}
