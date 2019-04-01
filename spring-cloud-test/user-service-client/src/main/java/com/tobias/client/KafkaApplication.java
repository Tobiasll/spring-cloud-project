package com.tobias.client;

import java.util.Properties;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaApplication {

  private static void execMsgSend() throws  Exception{
    Properties props = new Properties();
    props.put("bootstrap.servers", "47.107.127.145:9092");
    props.put("acks", "1");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    Producer<String, String> procuder = new KafkaProducer<>(props);

    String topic = "test";
    for (int i = 1; i <= 10; i++) {
      String value = " this is another message_" + i;
      ProducerRecord<String,String> record = new ProducerRecord<>(topic, i + "", value);
      procuder.send(record,
          (metadata, exception) -> System.out.println("message send to partition " + metadata.partition() + ", offset: " + metadata.offset()));
      System.out.println(i+" ----   success");
      Thread.sleep(1000);
    }
    System.out.println("send message over.");
    procuder.close();
  }


  public static void main(String[] args) throws Exception {

    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", "47.107.127.145:9092");
    properties.put("key.serializer", StringSerializer.class);
    properties.put("value.serializer", StringSerializer.class);

    KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
    Future<RecordMetadata> send = kafkaProducer.send(new ProducerRecord<>("users", 0, "message", "sdas"));
    System.out.println(send.get());

//    execMsgSend();
  }
}
