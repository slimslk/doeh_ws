package net.dimmid.kafka;

import net.dimmid.config.Config;
import net.dimmid.config.KafkaConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.Properties;

public class GameKafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(GameKafkaProducer.class);
    private final Producer<String, String> producer;

    public GameKafkaProducer() throws FileNotFoundException {
        Properties properties = KafkaConfiguration.getKafkaProperties();
        properties.put(ProducerConfig.ACKS_CONFIG, Config.getOrDefault("ACKS", "all"));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "ws_kafka_producer");
        this.producer = new KafkaProducer<>(properties);

    }

    public void produce(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<> (topic, key, message);
        logger.info("PRODUCER - Sending message to topic {} with key {} and message {}", topic, key, message);
        producer.send(record);
    }

    public void close() {
        producer.close();
    }
}