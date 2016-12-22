package wangmin.message.message_center_service.db.service;

import wangmin.message.core.entity.Message;
import wangmin.message.core.entity.MessageQuery;
import wangmin.message.core.entity.MessageStatus;

import java.util.Collection;
import java.util.List;

/**
 * <p>User: Wang Min
 * <p>Date: 2016-12-18
 * <p>Version: 1.0
 */
public interface MessageService {
    Message create(Message message);

    int updateById(Message message);
    int updateWithoutContentById(Message message);
    int updateByIdAndStatus(Message message, MessageStatus messageStatus);

    int deleteById(String id);
    int deleteByIds(List<String> msgIdList);

    Message findOneById(String id);
    Message findOneByIdAndStatus(String id, MessageStatus messageStatus);
    List<Message> findListByIds(Collection<String> ids);
    List<Message> findListByIdsAndStatus(Collection<String> ids, MessageStatus messageStatus);
    List<Message> findAll();
    List<String> findIdByMessageQuery(MessageQuery messageQuery);
    List<Message> findByMessageQuery(MessageQuery messageQuery);
    List<Message> findUnconsumedMessage(int limit);
    List<String> findUnconsumedMessageId(int limit);
}
