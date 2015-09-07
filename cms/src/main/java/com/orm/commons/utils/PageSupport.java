package com.orm.commons.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageSupport extends PageRequest {
	private static final long serialVersionUID = 9103361204348868275L;

	public PageSupport(int page, int size) {
		super(page, size);
	}

	public PageSupport(int page, int size, Sort.Direction direction, String... properties) {
		super(page, size, direction, properties);
	}

	public PageSupport(int page, int size, Sort sort) {
		super(page, size, sort);
	}

	public PageSupport(Pageable pageable, Sort sort) {
		super(pageable.getPageNumber(), pageable.getPageSize(), sort);
	}
}
