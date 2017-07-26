package com.druid.service;

import java.util.List;

import com.common.Res;
import com.druid.bean.dto.AdminDto;
import com.druid.bean.dto.AdminListDto;
import com.druid.bean.req.AdminDataTablesReq;
import com.druid.bean.req.AdminReq;

public interface AdminService {
	/**
	 * 账号登陆
	 * @param adminAccount
	 * @return
	*/
	public Res<AdminDto> adminLogin(String adminAccount, String adminPwd);
	
	/**
	 * 管理员列表
	 * @param AdminDataTablesReq
	 * @return
	 */
	public List<AdminListDto> adminList(AdminDataTablesReq req);
	
	/**
	 * 管理员列表长度
	 * @param AdminDataTablesReq
	 * @return
	 */
	public int adminCount(AdminDataTablesReq req);

	/**
	 * 新增管理员账号
	 * @param AdminReq
	 * @return
	*/
	public Res<Integer> addAdmin(AdminReq req);
	
	/**
	 * 修改管理员账号
	 * @param AdminReq
	 */
	public Res<Integer> modifyAdmin(AdminReq req);
	
	/**
	 * 逻辑删除管理员账号
	 * @param adminCode
	 * @return
	 */
	public Res<Integer> delAdmin(String adminCodes);
	
	/**
	 * 账号登陆
	 * @param adminCode
	 * @return
	*/
	public Res<AdminDto> adminDetail(String adminCode);
	
	/**
	 * 修改管理员密码
	 * @param adminCode
	 * @param adminPwd
	 */
	public Res<Integer> modifyAdminPwd(String adminCode, String adminPwd, String adminOldPwd);
	
	/**
	 * 重置管理员密码
	 * @param adminCode
	 */
	public Res<Integer> resetAdminPwd(String adminCode);
}
