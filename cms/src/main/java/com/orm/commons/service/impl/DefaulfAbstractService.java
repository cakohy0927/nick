package com.orm.commons.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.orm.commons.exception.ServiceException;
import com.orm.commons.service.BaseService;
import com.orm.commons.service.CakoHyJpaRepostiory;

@Transactional(readOnly = true)
public abstract class DefaulfAbstractService<E, ID extends Serializable> implements BaseService<E, ID> {
	
	@Autowired
	private CakoHyJpaRepostiory<E, ID> dao;

	// public void setDao(CakoHyJpaRepostiory<E, ID> dao) {
	// this.dao = dao;
	// }

	public E get(ID id) throws ServiceException{
		return (E) this.dao.findOne(id);
	}

	@Transactional(readOnly = false)
	public E save(E entity) throws ServiceException{
		return (E) this.dao.saveEntity(entity);
	}

	public Page<E> queryPageByMap(Map<String, Object> map, Pageable pageable) throws ServiceException{
		return this.dao.queryPageByMap(map, pageable);
	}

	public List<E> queryByMap(Map<String, Object> map) throws ServiceException{
		return this.dao.queryByMap(map);
	}

	@Transactional(readOnly = false)
	public void delete(ID id) throws ServiceException{
		E t = get(id);
		if (t != null) {
			this.dao.delete(id);
		}
	}

	@Transactional(readOnly = false)
	public void deleteAll() throws ServiceException{
		this.dao.deleteAll();
	}

	public void delete(Iterable<E> entities) throws ServiceException{
		this.dao.delete(entities);
	}

	@SuppressWarnings("unchecked")
	public void deleteBatch(String[] ids) throws ServiceException{
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				delete((ID) id);
			}
		}
	}
	
	@Override
	public List<E> queryByMap(Map<String, Object> paramMap, Sort sort) throws ServiceException {
		return dao.queryByMap(paramMap,sort);
	}
	
	
	@Override
	public List<E> findAll() throws ServiceException {
		return dao.findAll();
	}
}