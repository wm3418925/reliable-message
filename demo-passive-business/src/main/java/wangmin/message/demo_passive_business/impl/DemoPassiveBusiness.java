package wangmin.message.demo_passive_business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wangmin.message.core.entity.Message;
import wangmin.message.demo_passive_business_core.remote.DemoPassiveBusinessInterface;

/**
 * Created by wm on 2016/12/25.
 */
@Service("demoPassiveBusiness")
public class DemoPassiveBusiness implements DemoPassiveBusinessInterface {
    private static final Logger logger = LoggerFactory.getLogger(DemoPassiveBusiness.class);

    @Override
    public boolean test(Message message) {
        logger.info("message="+message);
        return (System.currentTimeMillis() & 1) == 0;
    }
    @Override
    public boolean test(String str) {
        logger.info("str="+str);
        return (System.currentTimeMillis() & 1) == 0;
    }
}
