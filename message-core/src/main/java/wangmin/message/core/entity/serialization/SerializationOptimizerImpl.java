package wangmin.message.core.entity.serialization;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;

import wangmin.message.core.entity.Message;
import wangmin.message.core.entity.MessageQuery;
import wangmin.message.core.entity.MessageStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This class must be accessible from both the provider and consumer
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

    @SuppressWarnings("rawtypes")
	public Collection<Class> getSerializableClasses() {
        List<Class> classes = new ArrayList<Class>(16);

        classes.add(MessageStatus.class);
        classes.add(Message.class);
        classes.add(MessageQuery.Field.class);
        classes.add(MessageQuery.CompareOperator.class);
        classes.add(MessageQuery.FieldCompareElement.class);
        classes.add(MessageQuery.class);
        return classes;
    }
}
