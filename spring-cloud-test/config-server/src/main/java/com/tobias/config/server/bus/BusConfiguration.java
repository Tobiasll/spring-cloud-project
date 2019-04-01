package com.tobias.config.server.bus;

import org.springframework.cloud.bus.SpringCloudBusClient;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.cloud.bus.event.EnvironmentChangeRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class BusConfiguration {

  @EventListener
  public void onRefreshRemoteApplicationEvent(RefreshRemoteApplicationEvent event) {
    System.out.printf("RefreshRemoteApplicationEvent - " +
        " Source : %s , originService : %s , destinationService : %s \n",
        event.getSource(), event.getOriginService(), event.getDestinationService());
  }

  @EventListener
  public void onEnvironmentChangeRemoteApplicationEvent(EnvironmentChangeRemoteApplicationEvent event) {
    System.out.printf("EnvironmentChangeRemoteApplicationEvent - " +
            " Source : %s , originService : %s , destinationService : %s \n",
        event.getSource(), event.getOriginService(), event.getDestinationService());
  }


  @StreamListener(SpringCloudBusClient.OUTPUT)
  public void onAckRemoteApplicationEvent(AckRemoteApplicationEvent event) {
    System.out.printf("AckRemoteApplicationEvent - " +
            " Source : %s , originService : %s , destinationService : %s \n",
        event.getSource(), event.getOriginService(), event.getDestinationService());
  }

}
