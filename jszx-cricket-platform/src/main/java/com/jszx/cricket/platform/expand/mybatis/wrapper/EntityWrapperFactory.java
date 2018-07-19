package com.jszx.cricket.platform.expand.mybatis.wrapper;

import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.CollectionWrapper;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import com.jszx.cricket.platform.module.entity.BaseEntity;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version   1.0    
 * @author   2724216806@qq.com
 * @date 2018年3月22日 下午5:56:13
 * 
 */

public class EntityWrapperFactory implements ObjectWrapperFactory{

	@Override
	public boolean hasWrapperFor(Object object) {
		return true;
	}

	@Override
	public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
		try {
			if (object instanceof ObjectWrapper) {
			      return (ObjectWrapper) object;
			    }else if(object instanceof BaseEntity){
			    	return new EntityWrapper(metaObject, (BaseEntity)object);
			    }else if (object instanceof Map) {
			    	return new MapWrapper(metaObject, (Map) object);
			    } else if (object instanceof Collection) {
			    	return new CollectionWrapper(metaObject, (Collection) object);
			    }else {
			    	return new BeanWrapper(metaObject, object);
			    }
		} catch (SecurityException | IllegalArgumentException e) {
			return metaObject.getObjectWrapper();
		}
	}

}
