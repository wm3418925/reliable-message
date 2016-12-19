package wangmin.message.unconfirmed_message_checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by wm on 2016/12/20.
 */
@Component
public class CheckerMonitor implements ApplicationListener {
    @Autowired
    private Checker checker;

    private Thread checkerThread;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            checkerThread = new Thread(checker, "unconfirmed-message-checker");
            checkerThread.start();
        } else if (event instanceof ContextStoppedEvent) {
            if (null != checkerThread)
                checker.stop();
        }
    }
}
