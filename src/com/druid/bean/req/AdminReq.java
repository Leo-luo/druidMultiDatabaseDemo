package com.druid.bean.req;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class AdminReq {
	@ApiModelProperty(value = "编码")
	private String adminCode;
	@ApiModelProperty(value = "账号")
	private String adminAccount;
	@ApiModelProperty(value = "密码")
	private String adminPwd;
	@ApiModelProperty(value = "名称")
	private String adminName;
	@ApiModelProperty(value = "角色编码")
	private String roleCode;
	@ApiModelProperty(value = "人员类型")
	private int adminType;
	@ApiModelProperty(value = "联系方式")
	private String adminPhone;
	
	public String getAdminCode() {
		return adminCode;
	}
	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}
	public String getAdminAccount() {
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}
	public String getAdminPwd() {
		return adminPwd;
	}
	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public int getAdminType() {
		return adminType;
	}
	public void setAdminType(int adminType) {
		this.adminType = adminType;
	}
	public String getAdminPhone() {
		return adminPhone;
	}
	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
}
