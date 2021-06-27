package org.example.api;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaProducerService {

//    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    public String sendMessageToTopic(String topicName, String message) {
//        logger.debug("Entering KafkaProducerService.sendMessageToTopic Method");
        String status;
        try {
            final Producer<String, String> producer;
            final Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producer = new KafkaProducer<>(props);
            producer.send(new ProducerRecord<>(topicName, message));
            producer.close();
            status = "success";
//            logger.info("status is: {}", status);
//            logger.info("Leaving KafkaProducerService.sendMessageToTopic Method");
        } catch (Exception e) {
//            logger.error("Exception: Error sending data to topic ", topicName,
//                    e.getMessage());
            status = "error";
//            logger.error("status is: {}", status);
            return status;
        }
        return status;
    }
}
