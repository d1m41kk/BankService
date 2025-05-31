package Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendClientEvent(String id, String jsonData) {
        kafkaTemplate.send("client-topic", id, jsonData);
    }

    public void sendAccountEvent(String id, String jsonData) {
        kafkaTemplate.send("account-topic", id, jsonData);
    }
}
