package com.orm.commons.utils;

import org.apache.commons.lang3.StringUtils;

public class Pager {

	private int totalPage;// 总页数
	private int totlaRecord;// 总计路数
	private int currentPage;// 当前页数
	private int pageSize = 10;

	public Pager(long totalRecord, String currentPage) {
		this.totlaRecord = (int) totalRecord;
		int currentpage = 1;
		if (StringUtils.isNotEmpty(currentPage)) {
			currentpage = Integer.parseInt(currentPage);
		}
		this.currentPage = currentpage;
		int totalPage = (int)totlaRecord / this.pageSize;
		if (totlaRecord % this.pageSize != 0)
			totalPage += 1;
		this.totalPage = totalPage;
	}

	public int getTotalPage() {
		totalPage = totlaRecord / pageSize;
		if (totlaRecord % pageSize != 0)
			totalPage += 1;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotlaRecord() {
		return totlaRecord;
	}

	public void setTotlaRecord(int totlaRecord) {
		this.totlaRecord = totlaRecord;
	}

	public int getCurrentPage() {
		if (currentPage <= 0)
			currentPage = 1;
		else if (currentPage > totalPage)
			currentPage = totalPage;
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获得开始位置
	 * 
	 * @return
	 */
	public int getStartPosition() {
		return (currentPage - 1) * pageSize;
	}

	/**
	 * 获得结束为止
	 * 
	 * @return
	 */
	public int getEndPosition() {
		int endConlum = 0;
		if (currentPage * pageSize > totlaRecord)
			endConlum = totlaRecord;
		else
			endConlum = currentPage * pageSize;
		return endConlum;
	}
}
