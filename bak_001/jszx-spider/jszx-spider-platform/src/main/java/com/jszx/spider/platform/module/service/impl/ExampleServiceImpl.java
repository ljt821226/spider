package com.jszx.spider.platform.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jszx.spider.platform.code.ReturnCode;
import com.jszx.spider.platform.exception.ServiceException;
import com.jszx.spider.platform.module.dao.ExampleDao;
import com.jszx.spider.platform.module.entity.ExampleEntity;
import com.jszx.spider.platform.module.entity.PageEntity;
import com.jszx.spider.platform.module.service.ExampleService;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月18日 下午2:35:16
 * 
 */

@Service("com.jszx.spider.platform.service.example")
public class ExampleServiceImpl implements ExampleService {

	@Autowired
	private ExampleDao exampleDao;

	@Override
	public ExampleEntity select(ExampleEntity entity) throws ServiceException {
		try {
			return entity;
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public ExampleEntity[] selectBatch(ExampleEntity entity) throws ServiceException {
		try {
			return exampleDao.selectBatch(entity);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public ExampleEntity[] selectPage(ExampleEntity entity, PageEntity page) throws ServiceException {
		try {
			return exampleDao.selectPage(entity, page);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public int insert(ExampleEntity entity) throws ServiceException {
		try {
			return exampleDao.insert(entity);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public int insertBatch(ExampleEntity[] entities) throws ServiceException {
		try {
			// return exampleDao.insertBatch(entities);
			return 1;
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public int update(ExampleEntity entity) throws ServiceException {
		try {
			return exampleDao.update(entity);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public int updateBatch(ExampleEntity[] entities) throws ServiceException {
		try {
			return exampleDao.updateBatch(entities);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public int delete(ExampleEntity entity) throws ServiceException {
		try {
			return exampleDao.delete(entity);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public int deleteBatch(ExampleEntity[] entities) throws ServiceException {
		try {
			return exampleDao.deleteBatch(entities);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

}
