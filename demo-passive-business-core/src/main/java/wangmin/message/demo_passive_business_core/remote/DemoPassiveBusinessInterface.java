package wangmin.message.demo_passive_business_core.remote;

import wangmin.message.core.entity.Message;

public interface DemoPassiveBusinessInterface {
	boolean test(Message message);
	boolean test(String str);
}
