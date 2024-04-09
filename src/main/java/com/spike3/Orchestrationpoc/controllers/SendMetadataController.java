package com.spike3.Orchestrationpoc.controllers;
import com.spike3.Orchestrationpoc.services.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metadata/send")
public class SendMetadataController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping
    public String sendMessageToKafka(@RequestBody String message) {
        kafkaProducerService.produceMessage(message);
        return "Message sent to Kafka topic successfully!";
    }
}
