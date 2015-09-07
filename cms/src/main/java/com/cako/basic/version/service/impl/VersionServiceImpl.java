package com.cako.basic.version.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cako.basic.version.dao.VersionDao;
import com.cako.basic.version.entity.Version;
import com.cako.basic.version.service.IVersionService;

@Component
@Transactional(readOnly = false)
public class VersionServiceImpl implements IVersionService {

	@Autowired
	private VersionDao versionDao;
	
	@Override
	public Version findOne(String id) {
		return versionDao.findOne(id);
	}

	@Override
	public List<Version> findAll() {
		return versionDao.findAll();
	}

	@Override
	public Version save(Version version) {
		return versionDao.saveAndFlush(version);
	}

	@Override
	public Page<Version> queryPageByMap(Map<String, Object> map, Pageable pageable) {
		return versionDao.queryPageByMap(map, pageable);
	}

	@Override
	public List<Version> getVersions(List<String> versionIds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id_in", versionIds);
		return versionDao.queryByMap(paramMap );
	}

}
