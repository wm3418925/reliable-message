package wangmin.message.unconsumed_message_checker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wangmin.message.core.entity.Message;
import wangmin.message.core.remote.MessageServiceInterface;

import java.util.List;

/**
 * Created by wm on 2016/12/18.
 */
@Service
public class Checker implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(Checker.class);

    // 两次检查的时间间隔 (单位毫秒)
    @Value(value = "${message.check.interval}")
    private long checkInterval;
    // 每个被发送的消息, 减少多少发送间隔 (单位毫秒)
    @Value(value = "${send_one_msg.minus.interval}")
    private long sendOneMsgMinusInterval;

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
                List<String> list = messageService.findUnconsumedMessageId(200);
                if (null != list && !list.isEmpty())
                    sendCount = messageService.sendMessagesById(list);
            } catch (Exception e) {
                logger.warn("", e);
            }

            logger.debug("checked count="+sendCount);

            try {
                long sleepMillis = checkInterval - sendCount*sendOneMsgMinusInterval;
                if (sleepMillis > 0)
                    Thread.sleep(sleepMillis);
            } catch (Exception e) {
                logger.warn("", e);
            }
        }
    }
}
