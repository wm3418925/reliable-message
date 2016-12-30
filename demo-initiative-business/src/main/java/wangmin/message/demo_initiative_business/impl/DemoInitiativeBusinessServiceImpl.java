package wangmin.message.demo_initiative_business.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import wangmin.demo_initiative_business.core.remote.DemoInitiativeBusinessServiceInterface;

@Service("demoInitiativeBusinessService")
public class DemoInitiativeBusinessServiceImpl implements DemoInitiativeBusinessServiceInterface {

	@Override
	public boolean isMessageBusinessExist(String msgId) {
		return StringUtils.isNotEmpty(msgId) && (msgId.charAt(msgId.length()-1) & 1) == 0;
	}
}
