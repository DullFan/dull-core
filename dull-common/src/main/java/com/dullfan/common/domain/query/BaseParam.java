package com.dullfan.common.domain.query;

public class BaseParam {
	private SimplePage simplePage;
	private Integer pageNum;
	private Integer pageSize;
	private String orderBy;
	public SimplePage getSimplePage() {
		return simplePage;
	}
	public void setSimplePage(SimplePage simplePage) {
		this.simplePage = simplePage;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public void setOrderBy(String orderBy){
		this.orderBy = orderBy;
	}
	public String getOrderBy(){
		return this.orderBy;
	}
}
