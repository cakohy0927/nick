package com.orm.commons.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract interface CakoHyJpaRepostiory<T, ID extends Serializable> extends JpaRepository<T, ID> {

	public abstract List<T> queryByMap(Map<String, Object> paramMap);

	public abstract List<T> queryByCriteria(Criteria paramCriteria);

	public abstract List<T> queryByCriteria(Criteria paramCriteria, int paramInt);

	public abstract List<T> queryByCriteria(Criteria paramCriteria, Sort paramSort);

	public abstract Page<T> queryByPage(Pageable paramPageable);

	public abstract Page<T> queryPageByMap(Map<String, Object> paramMap, Pageable paramPageable);

	public abstract Page<T> queryPageByCriteria(Criteria paramCriteria, Pageable paramPageable);

	public abstract Criteria createCriteria(Map<String, Object> paramMap);

	public abstract Disjunction createdDisjunction(Map<String, Object> paramMap);

	public abstract int nativeSqlUpdate(String paramString, Object... paramVarArgs);

	public abstract int nativeSqlUpdate(String paramString, Map<String, ?> paramMap);

	public abstract T saveEntity(T paramT);

	public abstract void insertInBatch(List<T> paramList);

	public abstract List<T> findByEntityList(Map<String, Object> paramMap);
	
	public abstract List<T> queryByMap(Map<String, Object> paramMap,Sort sort);

}
