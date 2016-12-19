package wangmin.message.demo_passive_business.impl;

import org.springframework.stereotype.Service;

import wangmin.message.core.entity.MyUserEntity;
import wangmin.message.core.remote.MyRemoteServiceInterface;

@Service("myRemoteService")
public class MyRemoteServiceImpl implements MyRemoteServiceInterface {

	public MyUserEntity getUserById(String id) {
		System.out.println("getUserById : " + id);
		return null;
	}
}
