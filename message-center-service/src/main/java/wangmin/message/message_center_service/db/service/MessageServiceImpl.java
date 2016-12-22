package wangmin.message.message_center_service.db.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import wangmin.message.core.entity.Message;
import wangmin.message.core.entity.MessageQuery;
import wangmin.message.core.entity.MessageStatus;
import wangmin.message.message_center_service.db.dao.MessageDao;

@Service
public class MessageServiceImpl implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
    @Autowired
    private MessageDao messageDao;


	@Override
	public Message create(Message message) {
		return messageDao.create(message);
	}

	@Override
	public int updateById(Message message) {
		return messageDao.updateById(message);
	}
	@Override
	public int updateWithoutContentById(Message message) {
		return messageDao.updateWithoutContentById(message);
	}
	@Override
	public int updateByIdAndStatus(Message message, MessageStatus messageStatus) {
		return messageDao.updateByIdAndStatus(message, messageStatus);
	}

	@Override
	public int deleteById(String id) {
		return messageDao.deleteById(id);
	}
	@Override
	public int deleteByIds(List<String> msgIdList) {
		return messageDao.deleteByIds(msgIdList);
	}

	@Override
	public Message findOneById(String id) {
		return messageDao.findOneById(id);
	}
	@Override
	public Message findOneByIdAndStatus(String id, MessageStatus messageStatus) {
		return messageDao.findOneByIdAndStatus(id, messageStatus);
	}
	@Override
	public List<Message> findListByIds(Collection<String> ids) {
		return messageDao.findListByIds(ids);
	}
	@Override
	public List<Message> findListByIdsAndStatus(Collection<String> ids, MessageStatus messageStatus) {
		return messageDao.findListByIdsAndStatus(ids, messageStatus);
	}
	@Override
	public List<Message> findAll() {
		return messageDao.findAll();
	}
	@Override
	public List<String> findIdByMessageQuery(MessageQuery messageQuery) {
		return messageDao.findIdByMessageQuery(messageQuery);
	}
	@Override
	public List<Message> findByMessageQuery(MessageQuery messageQuery) {
		return messageDao.findByMessageQuery(messageQuery);
	}
	@Override
	public List<Message> findUnconsumedMessage(int limit) {
		return messageDao.findUnconsumedMessage(limit);
	}
	@Override
	public List<String> findUnconsumedMessageId(int limit) {
		return messageDao.findUnconsumedMessageId(limit);
	}

}
