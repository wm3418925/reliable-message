package wangmin.message.demo_message_business_consumer.message;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wangmin.message.demo_passive_business_core.remote.DemoPassiveBusinessInterface;

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
    private DemoPassiveBusinessInterface demoPassiveBusiness;

    @Autowired
    public MessageConsumerMonitor(
            @Value(value = "${kafka.consumer.count}") int consumerCount,
            @Value(value = "${kafka.topics}") String topicsStr,
            @Value(value = "${kafka.group.id}") String groupId,
            @Value(value = "${kafka.bootstrap.servers}") String bootstrapServers,
            @Value(value = "${kafka.key.serializer}") String keySerializer,
            @Value(value = "${kafka.value.serializer}") String valueSerializer) {
        this.consumerCount = consumerCount;
        this.topics = Lists.newArrayList(topicsStr.split(","));
        this.groupId = groupId;

        props = new Properties();
        props.put("group.id", groupId);
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.deserializer", keySerializer);
        props.put("value.deserializer", valueSerializer);

        startAllConsumers();
    }

    private void startAllConsumers() {
        final ExecutorService executor = Executors.newFixedThreadPool(consumerCount);

        final List<MessageConsumer> consumers = Lists.newArrayList();
        for (int i = 0; i < consumerCount; i++) {
            MessageConsumer consumer = new MessageConsumer(demoPassiveBusiness, topics, groupId, props);
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
