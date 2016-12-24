package wangmin.message.message_center_service.send_message;

import java.util.*;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wangmin.message.core.entity.Message;

/**
 * Created by wm on 2016/12/23.
 */
@Service
public class KafkaMessageSender implements MessageSenderInterface {
    private KafkaProducer<String, String> producer;
    @Autowired
    public KafkaMessageSender(
            @Value(value = "${kafka.bootstrap.servers}") String bootstrapServers,
            @Value(value = "${kafka.key.serializer}") String keySerializer,
            @Value(value = "${kafka.value.serializer}") String valueSerializer) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", keySerializer);
        props.put("value.serializer", valueSerializer);

        producer = new KafkaProducer<>(props);
    }

    @Override
    public boolean sendMessage(Message message) {
        ProducerRecord<String, String> data = new ProducerRecord<>(message.queue, message.id, message.content);
        producer.send(data);
        return true;
    }

    @Override
    public void close() {
        producer.close();
    }
}
