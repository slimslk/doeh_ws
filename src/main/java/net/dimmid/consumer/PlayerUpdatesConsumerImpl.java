package net.dimmid.consumer;

import net.dimmid.kafka.GameKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class PlayerUpdatesConsumerImpl implements GameConsumer {
    private static final Logger logger = LoggerFactory.getLogger(PlayerUpdatesConsumerImpl.class);
    private final KafkaConsumer<String, String> consumer;
    private final BlockingQueue<Map<String, String>> outputQueue;


    public PlayerUpdatesConsumerImpl(GameKafkaConsumer consumer,
                                     BlockingQueue<Map<String, String>> playerUpdatesOutputQueue) {
        this.consumer = consumer.getConsumer();
        this.outputQueue = playerUpdatesOutputQueue;
    }

    @Override
    public ConsumerRecords<String, String> poll_records() {
        return consumer.poll(Duration.ofMillis(100));
    }

    @Override
    public void processRecord(ConsumerRecord<String, String> record) {
        Map<String, String> map = Map.of("userId", record.key(), "value", record.value());
        for(Map.Entry<String, String> entry : map.entrySet()) {
            logger.trace("CONSUMER - Player updates: {} {}", entry.getKey(), entry.getValue());
        }
        try {
            outputQueue.put(map);
        }
        catch (InterruptedException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void stop() {
        consumer.close();
    }

}
