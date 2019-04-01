package com.tobias.client;

import com.tobias.api.UserService;
import com.tobias.client.stream.UserMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringCloudApplication
@RibbonClients({
    @RibbonClient("user-provider-service")
})
@EnableFeignClients(clients = UserService.class)
@EnableBinding(UserMessage.class)
public class UserServiceClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceClientApplication.class, args);
  }

}
