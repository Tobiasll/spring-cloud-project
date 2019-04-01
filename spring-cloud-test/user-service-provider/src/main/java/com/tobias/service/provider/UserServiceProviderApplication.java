package com.tobias.service.provider;

import com.tobias.service.provider.stream.UserMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;


@SpringCloudApplication
@EnableHystrix
@EnableBinding(UserMessage.class)
public class UserServiceProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceProviderApplication.class, args);
  }

}
