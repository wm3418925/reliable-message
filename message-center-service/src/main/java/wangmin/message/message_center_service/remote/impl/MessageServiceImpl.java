package wangmin.message.message_center_service.remote.impl;

import org.springframework.beans.factory.annotation.Autowired;
import wangmin.message.core.entity.*;
import wangmin.message.core.remote.MessageServiceInterface;
import org.springframework.stereotype.Service;
import wangmin.message.message_center_service.service.MessageService;

import java.util.Date;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageServiceInterface {
	@Autowired
	private MessageService messageService;

	@Override
	public Message createMessage(String msgId, String source, String queue, String content, MessageStatus status) {
		Message message = new Message(msgId, source, queue, content, 0, status, new Date(), new Date());
		return messageService.create(message);
	}

	@Override
	public boolean killMessage(String msgId) {
		Message msg = messageService.findOneById(msgId);
		if (null == msg)
			return false;

		msg.status = MessageStatus.MessageStatus_dead;
		msg.updateTime = new Date();
		return 1 == messageService.updateById(msg);
	}
	@Override
	public boolean closeMessage(String msgId) {
		Message msg = messageService.findOneById(msgId);
		if (null == msg)
			return false;

		msg.status = MessageStatus.MessageStatus_end;
		msg.updateTime = new Date();
		return 1 == messageService.updateById(msg);
	}
	@Override
	public boolean reliveMessage(String msgId) {
		Message msg = messageService.findOneByIdAndStatus(msgId, MessageStatus.MessageStatus_dead);
		if (null == msg)
			return false;

		msg.status = MessageStatus.MessageStatus_confirmed;
		msg.updateTime = new Date();
		return 1 == messageService.updateById(msg);
	}

	private boolean confirmAndSendMessage(Message msg) {
		// TODO 发送消息

		msg.status = MessageStatus.MessageStatus_sending;
		msg.updateTime = new Date();
		return 1 == messageService.updateById(msg);
	}
	@Override
	public boolean confirmAndSendMessage(String msgId) {
		Message msg = messageService.findOneByIdAndStatus(msgId, MessageStatus.MessageStatus_unconfirmed);
		if (null == msg)
			return false;

		return confirmAndSendMessage(msg);
	}
	@Override
	public int confirmAndSendMessages(List<Message> msgList) {
		int res = 0;
		for (Message msg : msgList) {
			if (confirmAndSendMessage(msg))
				++res;
		}
		return res;
	}
	@Override
	public int confirmAndSendMessagesById(List<String> msgIdList) {
		List<Message> msgList = messageService.findListByIdsAndStatus(msgIdList, MessageStatus.MessageStatus_unconfirmed);
		if (null == msgList)
			return 0;

		return confirmAndSendMessages(msgList);
	}
	@Override
	public boolean sendMessage(Message msg) {
		if (msg.retry >= 5) {
			msg.status = MessageStatus.MessageStatus_dead;
			msg.updateTime = new Date();
			messageService.updateById(msg);
			return false;
		}

		// TODO 发送消息

		msg.status = MessageStatus.MessageStatus_sending;
		++msg.retry;
		msg.updateTime = new Date();
		return 1 == messageService.updateById(msg);
	}
	@Override
	public boolean sendMessage(String msgId) {
		Message message = messageService.findOneById(msgId);
		if (null == message)
			return false;

		return sendMessage(message);
	}
	@Override
	public int sendMessages(List<Message> msgList) {
		int res = 0;

		for (Message msg : msgList) {
			if (sendMessage(msg))
				++res;
		}

		return res;
	}
	@Override
	public int sendMessagesById(List<String> msgIdList) {
		return sendMessages(messageService.findListByIds(msgIdList));
	}

	@Override
	public int removeMessages(List<String> msgIdList) {
		return messageService.deleteByIds(msgIdList);
	}

	@Override
	public Message queryMessage(String msgId) {
		return messageService.findOneById(msgId);
	}

	@Override
	public List<String> queryMessageIdList(MessageQuery messageQuery) {
		return messageService.findIdByMessageQuery(messageQuery);
	}
	@Override
	public List<Message> queryMessageList(MessageQuery messageQuery) {
		return messageService.findByMessageQuery(messageQuery);
	}
	@Override
	public List<Message> findUnconsumedMessage(int limit) {
		return messageService.findUnconsumedMessage(limit);
	}
	@Override
	public List<String> findUnconsumedMessageId(int limit) {
		return messageService.findUnconsumedMessageId(limit);
	}
}
