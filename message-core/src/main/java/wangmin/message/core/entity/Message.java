package wangmin.message.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wm on 2016/12/18.
 */
public class Message implements Serializable {
    public String id;
    public String source;
    public String queue;
    public String content;
    public int retry;
    public MessageStatus status;
    public Date createTime;
    public Date updateTime;


    public Message() {}
    public Message(String id, String source, String queue, String content, int retry, MessageStatus status, Date createTime, Date updateTime) {
        this.id = id;
        this.source = source;
        this.queue = queue;
        this.content = content;
        this.retry = retry;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        return      "id=" + id
                + ", source=" + source
                + ", queue=" + queue
                + ", content=" + content
                + ", retry=" + retry
                + ", status=" + status
                + ", createTime=" + createTime
                + ", updateTime=" + updateTime;
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
