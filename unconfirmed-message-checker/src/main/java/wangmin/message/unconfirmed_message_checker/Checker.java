package wangmin.message.unconfirmed_message_checker;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wangmin.message.core.entity.Message;
import wangmin.message.core.entity.MessageQuery;
import wangmin.message.core.entity.MessageStatus;
import wangmin.message.core.remote.MessageServiceInterface;

import java.util.List;

/**
 * Created by wm on 2016/12/20.
 */
@Service
public class Checker implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(Checker.class);

    // 两次检查的时间间隔 (单位毫秒)
    @Value(value = "${message.check.interval}")
    private long checkInterval;
    // 每个被发送的消息, 减少多少发送间隔 (单位毫秒)
    @Value(value = "${confirm_one_msg.minus.interval}")
    private long confirmOneMsgMinusInterval;

    @Autowired
    private Confirmer confirmer;

    @Autowired
    private MessageServiceInterface messageService;

    public Checker() {
    }

    private volatile boolean runFlag = true;
    public void stop() {
        runFlag = false;
    }
    @Override
    public void run() {
        while (runFlag) {
            int sendCount = 0;
            try {
                MessageQuery messageQuery = new MessageQuery()
                        .whereStatus(MessageQuery.CompareOperator.equal, MessageStatus.MessageStatus_unconfirmed)
                        .orderBy(MessageQuery.Field.createTime, false)
                        .limit(200);
                List<Message> unconfirmedList = messageService.queryMessageList(messageQuery);

                if (null != unconfirmedList && !unconfirmedList.isEmpty()) {
                    List<Message> confirmedList = Lists.newArrayList();
                    List<String> notExistIdList = Lists.newArrayList();
                    for (Message msg : unconfirmedList) {
                        Boolean confirmResult = confirmer.confirm(msg);

                        if (null != confirmResult) {
                            if (confirmResult)
                                confirmedList.add(msg);
                            else
                                notExistIdList.add(msg.id);
                        }
                    }

                    try {
                        int removeCount = messageService.removeMessages(notExistIdList);
                        logger.info("removed confirmed not exist msg count={}, list={}", removeCount, notExistIdList);
                    } catch (Exception e) {
                        logger.warn("", e);
                    }

                    sendCount = messageService.confirmAndSendMessages(confirmedList);
                }
            } catch (Exception e) {
                logger.warn("", e);
            }

            logger.debug("checked count="+sendCount);

            try {
                long sleepMillis = checkInterval - sendCount*confirmOneMsgMinusInterval;
                if (sleepMillis > 0)
                    Thread.sleep(sleepMillis);
            } catch (Exception e) {
                logger.warn("", e);
            }
        }
    }
}
