package wangmin.message.message_center_service.send_message;

import wangmin.message.core.entity.Message;

/**
 * Created by wm on 2016/12/23.
 */
public interface MessageSenderInterface {
    boolean sendMessage(Message message);
    void close();
}
