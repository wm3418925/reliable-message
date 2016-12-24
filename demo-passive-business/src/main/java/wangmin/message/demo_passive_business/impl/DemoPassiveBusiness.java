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
    public void test(Message message) {
        logger.info("message="+message);
    }
    @Override
    public void test(String str) {
        logger.info("str="+str);
    }
}
