package com.druid.bean.dto;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class AdminDto {
	@ApiModelProperty(value = "管理员ID")
	private int adminId;
	@ApiModelProperty(value = "编码")
	private String adminCode;
	@ApiModelProperty(value = "账号")
	private String adminAccount;
	@ApiModelProperty(value = "名称")
	private String adminName;
	@ApiModelProperty(value = "创建时间")
	private String createTime;
	@ApiModelProperty(value = "角色编码")
	private String roleCode;
	@ApiModelProperty(value = "类型")
	private int adminType;
	@ApiModelProperty(value = "联系方式")
	private String adminPhone;
	
	@ApiModelProperty(value = "角色名称")
	private String roleName;
	@ApiModelProperty(value = "模块编码串,','逗号隔开")
	private String moduleCodes;
	@ApiModelProperty(value = "模块名称串,','逗号隔开")
	private String moduleNames;
	@ApiModelProperty(value = "模块链接串,','逗号隔开")
	private String moduleConnects;
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
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
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getAdminPhone() {
		return adminPhone;
	}
	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
	public String getModuleCodes() {
		return moduleCodes;
	}
	public void setModuleCodes(String moduleCodes) {
		this.moduleCodes = moduleCodes;
	}
	public String getModuleNames() {
		return moduleNames;
	}
	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;
	}
	public String getModuleConnects() {
		return moduleConnects;
	}
	public void setModuleConnects(String moduleConnects) {
		this.moduleConnects = moduleConnects;
	}
}
