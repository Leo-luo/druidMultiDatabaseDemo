package com.common;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class Res<T> {

	@ApiModelProperty(value = "返回码")
    private String code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @ApiModelProperty(value = "返回的对象数据")
    private T data;// 返回的json数据类型

    public Res() {
        // no parameters
    }
    
    public Res(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

}
