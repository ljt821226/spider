package com.jszx.cricket.demo.module.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jszx.cricket.demo.module.dao.UserDao;
import com.jszx.cricket.demo.module.entity.UserEntity;
import com.jszx.cricket.demo.module.service.UserService;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.PageEntity;


@Service("com.jszx.demo.userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public UserEntity query(UserEntity entity) throws ServiceException {
		try {
			entity.set("token", "1234");
			return this.userDao.select(entity);
		} catch (Exception e) {
			throw new ServiceException(-1, "系统异常，请联系系统管理员...", e);
		}
	}

	public List<UserEntity> list(UserEntity entity) throws ServiceException {
		try {
			entity.set("token", "123");
			return this.userDao.list(entity);
		} catch (Exception e) {
			throw new ServiceException(-1, "系统异常，请联系系统管理员...", e);
		}
	}

	public List<UserEntity> page(UserEntity userEntity, PageEntity pageEntity) throws ServiceException {
		try {
			userEntity.set("token", "123");
			return this.userDao.page(userEntity, pageEntity);
		} catch (Exception e) {
			throw new ServiceException(-1, "系统异常，请联系系统管理员...", e);
		}
	}
}
