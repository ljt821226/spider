package com.jszx.cricket.demo.module.dao;

import com.jszx.cricket.demo.module.entity.UserEntity;
import com.jszx.cricket.platform.module.entity.PageEntity;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("com.jszx.demo.module.dao.userDao")
public abstract interface UserDao {
	public abstract UserEntity select(UserEntity paramUserEntity);

	public abstract List<UserEntity> list(UserEntity paramUserEntity);

	public abstract List<UserEntity> page(@Param("entity") UserEntity paramUserEntity,
			@Param("page") PageEntity paramPageEntity);
}
