package wangmin.message.core.entity;

/**
 * Created by wm on 2016/12/18.
 */
public enum MessageStatus {
    MessageStatus_unconfirmed(0),   // 待确认
    MessageStatus_confirmed(1),   // 确认未发送
    MessageStatus_sending(2),   // 发送中
    MessageStatus_dead(3),   // 死亡
    MessageStatus_end(4);   // 发送成功
    protected static final MessageStatus defaultEnum = MessageStatus_unconfirmed;

    protected final int value;
    private MessageStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
    public static int getValue(MessageStatus status) {
        if (status == null)
            return defaultEnum.value;
        else
            return status.value;
    }
    public static MessageStatus valueOf(int value) {
        MessageStatus[] list = MessageStatus.values();
        for (int i=0; i<list.length; ++i) {
            if (list[i].value == value)
                return list[i];
        }
        return defaultEnum;
    }
}
