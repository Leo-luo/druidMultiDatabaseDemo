package com.common;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class Req<T> {

	@ApiModelProperty(value = "请求的对象数据")
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
