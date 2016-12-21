package wangmin.message.demo_initiative_business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;
import wangmin.demo_initiative_business.core.Constants;
import wangmin.message.core.entity.MessageStatus;
import wangmin.message.core.remote.MessageServiceInterface;

import java.util.Random;

/**
 * Created by wm on 2016/12/21.
 */
@Component
public class TestLoopThread implements ApplicationListener,Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TestLoopThread.class);

    @Autowired
    private MessageServiceInterface messageService;

    private Thread checkerThread;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            checkerThread = new Thread(this, "unconsumed-message-checker");
            checkerThread.start();
        } else if (event instanceof ContextStoppedEvent) {
            this.stop();
        }
    }

    private volatile boolean runFlag = true;
    public void stop() {
        runFlag = false;
    }

    @Override
    public void run() {
        Random random = new Random(System.currentTimeMillis());

        while (runFlag) {
            String msgId = String.valueOf(System.currentTimeMillis());
            String source = Constants.messageSource;
            String queue = Constants.messageQueue;
            String content = String.valueOf(random.nextDouble());
            MessageStatus status = MessageStatus.MessageStatus_unconfirmed;

            messageService.createMessage(msgId, source, queue, content, status);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                logger.warn("", e);
            }

            if (random.nextBoolean())
                messageService.confirmAndSendMessage(msgId);
        }
    }
}
