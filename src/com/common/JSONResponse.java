package com.common;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class JSONResponse<T> {

	@ApiModelProperty(value = "返回码")
	private String code;
	@ApiModelProperty(value = "返回信息")
	private String msg;
	@ApiModelProperty(value = "当前记录条数")
    private long records;
	@ApiModelProperty(value = "总记录条数")
    private long totalRecords;
	@ApiModelProperty(value = "返回的对象数据列表")
    private List<T> dataList;

    public JSONResponse(String code, String msg, long records, long totalRecords, List<T> dataList) {
    	 this.code = code;
         this.msg = msg;
    	this.records = records;
        this.totalRecords = totalRecords;
        this.dataList = dataList;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

}
