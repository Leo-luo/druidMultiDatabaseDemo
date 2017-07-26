package com.common;

public enum ResultCodeEnum {
	
	OPERATION_SUCCESS("0000","操作成功"),
	
	IS_DELETED("9986","已删除"),
	
	ACCOUNT_IS_EXISTED("9987","账号已存在"),
	
	PASSWORD_ERROR("9988","密码错误"),
	
	ACCOUNT_NOT_EXIST("9989","账号不存在"),
	
	DATA_ERROR("9992","文件上传错误"),
	
	UPLOAD_ERROR("9993","文件上传错误"),
	
	PARAMETER_ERROR("9994","参数错误"),
	
	PIC_UPLOAD_ERROR("9995","图片上传失败"),
	
	MESS_SEND_ERROR("9996","短信发送失败"),
	
	PARAMETER_NULL("9997","必填参数为空"),
	
	NO_DATA("9998","无数据"),
	
	SYSTEM_ERROR("9999","系统错误");
	
	
	private String code;
	
	private String message;
	
	ResultCodeEnum(String code,String message){
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
