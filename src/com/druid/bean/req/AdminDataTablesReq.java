package com.druid.bean.req;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.wordnik.swagger.annotations.ApiParam;

public class AdminDataTablesReq {
	@ApiParam(value = "姓名")
	private String adminName;
	@ApiParam(value = "账号")
	private String adminAccount;
	@ApiParam(value = "创建日期:开始时间 yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
	@ApiParam(value = "创建日期:结束时间 yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
	
    @ApiParam(required = true, name = "start", value = "开始序号[0-N]")
    private int start;
    @ApiParam(required = true, name = "length", value = "每次请求的容量")
    private int length;
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminAccount() {
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

}
