package wangmin.message.demo_message_business_consumer.message;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wm on 2016/12/23.
 */
@Service
public class MessageConsumerMonitor {
    private final int consumerCount;
    private final List<String> topics;
    private final String groupId;
    private final Properties props;


    @Autowired
    public MessageConsumerMonitor(
            @Value(value = "${kafka.consumer.count}") int consumerCount,
            @Value(value = "${kafka.topics}") String topicsStr,
            @Value(value = "${kafka.group.id}") String groupId,
            @Value(value = "${kafka.bootstrap.servers}") String bootstrapServers,
            @Value(value = "${kafka.key.deserializer}") String keyDeserializer,
            @Value(value = "${kafka.value.deserializer}") String valueDeserializer) {
        this.consumerCount = consumerCount;
        this.topics = Lists.newArrayList(topicsStr.split(","));
        this.groupId = groupId;

        props = new Properties();
        props.put("group.id", groupId);
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.deserializer", keyDeserializer);
        props.put("value.deserializer", valueDeserializer);

        startAllConsumers();
    }

    private void startAllConsumers() {
        final ExecutorService executor = Executors.newFixedThreadPool(consumerCount);

        final List<MessageConsumer> consumers = Lists.newArrayList();
        for (int i = 0; i < consumerCount; i++) {
            MessageConsumer consumer = new MessageConsumer(topics, groupId, props);
            consumers.add(consumer);
            executor.submit(consumer);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (MessageConsumer consumer : consumers) {
                    consumer.shutdown();
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
