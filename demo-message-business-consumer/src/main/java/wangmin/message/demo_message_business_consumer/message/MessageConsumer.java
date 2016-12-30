package wangmin.message.demo_message_business_consumer.message;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wangmin.message.core.remote.MessageServiceInterface;
import wangmin.message.core.spring.SpringUtils;
import wangmin.message.demo_passive_business_core.remote.DemoPassiveBusinessInterface;

/**
 * Created by wm on 2016/12/23.
 */
public class MessageConsumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    private final MessageServiceInterface messageService = SpringUtils.getBean(MessageServiceInterface.class);
    private final DemoPassiveBusinessInterface demoPassiveBusiness = SpringUtils.getBean(DemoPassiveBusinessInterface.class);
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
                    /*Map<String, Object> data = new HashMap<>();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    data.put("key", record.key());*/

                    logger.info("received message, key={}, value={}", record.key(), record.value());
                    if (demoPassiveBusiness.test(record.value())) {
                        messageService.closeMessage(record.key());
                        logger.info("message closed, key={}, value={}", record.key(), record.value());
                    }
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
