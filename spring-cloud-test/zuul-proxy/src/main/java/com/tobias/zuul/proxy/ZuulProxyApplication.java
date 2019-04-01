package com.tobias.zuul.proxy;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication
@EnableZuulProxy
@EnableHystrix
public class ZuulProxyApplication {

  public static void main(String[] args) {
    SpringApplication.run(ZuulProxyApplication.class, args);
  }

}
