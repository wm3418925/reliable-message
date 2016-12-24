package wangmin.message.demo_message_business_consumer.message;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

/**
 * Created by wm on 2016/12/23.
 */
public class MessageConsumer implements Runnable {
    private final List<String> topics;
    private final String groupId;
    private final KafkaConsumer<String, String> consumer;


    public MessageConsumer(
            List<String> topics,
            String groupId,
            Properties props) {
        this.topics = topics;
        this.groupId = groupId;
        this.consumer = new KafkaConsumer<>(props);
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(topics);

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1<<12);
                for (ConsumerRecord<String, String> record : records) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    data.put("key", record.key());
                    System.out.println("data : " + data);
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        consumer.wakeup();
    }

}
