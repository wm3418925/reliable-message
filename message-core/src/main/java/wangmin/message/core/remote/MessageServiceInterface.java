package wangmin.message.core.remote;

import wangmin.message.core.entity.Message;
import wangmin.message.core.entity.MessageQuery;
import wangmin.message.core.entity.MessageStatus;

import java.util.List;

public interface MessageServiceInterface {
	Message createMessage(String msgId, String source, String queue, String content, MessageStatus status);

	boolean killMessage(String msgId);
	boolean closeMessage(String msgId);
	boolean reliveMessage(String msgId);
	boolean reliveAndSendMessage(String msgId);

	boolean confirmAndSendMessage(String msgId);
	int confirmAndSendMessages(List<Message> msgList);
	int confirmAndSendMessagesById(List<String> msgIdList);
	boolean sendMessage(Message msg);
	boolean sendMessage(String msgId);
	int sendMessages(List<Message> msgList);
	int sendMessagesById(List<String> msgIdList);

	int removeMessages(List<String> msgIdList);


	Message queryMessage(String msgId);
	List<String> queryMessageIdList(MessageQuery messageQuery);
	List<Message> queryMessageList(MessageQuery messageQuery);
	List<Message> findUnconsumedMessage(int limit);
	List<String> findUnconsumedMessageId(int limit);
}
