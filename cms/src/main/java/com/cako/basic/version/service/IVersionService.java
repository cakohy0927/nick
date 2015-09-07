package com.cako.basic.version.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cako.basic.version.entity.Version;

public interface IVersionService {

	
	public Version save(Version version);
	
	public Version findOne(String id);
	
	public List<Version> findAll();
	
	public List<Version> getVersions(List<String> versionIds);
	
	public Page<Version> queryPageByMap(Map<String, Object> map,Pageable pageable);
}
