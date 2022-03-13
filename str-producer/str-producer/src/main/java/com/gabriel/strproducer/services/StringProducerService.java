package com.gabriel.strproducer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class StringProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message){
        kafkaTemplate.send("str-topic", message).addCallback(
                success -> {
                    if(success != null) {
                        log.info("Send message with success {} ", message);
                        log.info("Partition {} ", success.getRecordMetadata().partition());
                        log.info("Offset {} ", success.getRecordMetadata().offset());
                    }
                },
                error -> log.error("Error sending message")
        );
    }
}
