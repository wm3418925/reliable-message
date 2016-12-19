package wangmin.message.core.remote;

import wangmin.message.core.entity.MyUserEntity;

public interface MyRemoteServiceInterface {
	MyUserEntity getUserById(String id);
}
