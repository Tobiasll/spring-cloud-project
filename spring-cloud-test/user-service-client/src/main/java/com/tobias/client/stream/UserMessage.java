package com.tobias.client.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserMessage {

  String OUTPUT = "user-message-output";

  @Output(OUTPUT)
  MessageChannel output();

}
