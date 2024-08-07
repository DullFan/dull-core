package com.dullfan.common.entity.query;


import com.dullfan.common.enums.PageSizeEnum;

public class SimplePage {
	private int pageNum;
	private int countTotal;
	private int pageSize;
	private int pageTotal;
	private int start;
	private int end;
	public SimplePage() {
	}
	public SimplePage(Integer pageNum, int countTotal, int pageSize) {
		if (null == pageNum) {
			pageNum = 0;
		}
		this.pageNum = pageNum;
		this.countTotal = countTotal;
		this.pageSize = pageSize;
		action();
	}
	public SimplePage(int start, int end) {
		this.start = start;
		this.end = end;
	}
	public void action() {
		if (this.pageSize <= 0) {
			this.pageSize = PageSizeEnum.SIZE20.getSize();
		}
		if (this.countTotal > 0) {
			this.pageTotal = this.countTotal % this.pageSize == 0 ? this.countTotal / this.pageSize
					: this.countTotal / this.pageSize + 1;
		} else {
			pageTotal = 1;
		}
		if (pageNum <= 1) {
			pageNum = 1;
		}
		if (pageNum > pageTotal) {
			pageNum = pageTotal;
		}
		this.start = (pageNum - 1) * pageSize;
		this.end = this.pageSize;
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	public int getPageTotal() {
		return pageTotal;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	public int getCountTotal() {
		return countTotal;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public void setCountTotal(int countTotal) {
		this.countTotal = countTotal;
		this.action();
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
