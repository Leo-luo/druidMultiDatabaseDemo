package com.druid.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.Res;
import com.common.ResultCodeEnum;
import com.druid.bean.TAdmin;
import com.druid.bean.dto.AdminDto;
import com.druid.bean.dto.AdminListDto;
import com.druid.bean.req.AdminDataTablesReq;
import com.druid.bean.req.AdminReq;
import com.druid.dao.AdminDao;
import com.druid.service.AdminService;
import com.utils.BeanUtil;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;

	@Override
	public Res<AdminDto> adminLogin(String adminAccount, String adminPwd) {
		// TODO Auto-generated method stub
		TAdmin admin = adminDao.adminDetailByAccount(adminAccount);
		if(admin!=null){
			if(admin.getAdminPwd().equals(adminPwd)){
				if(admin.getAdminStatus()!=1){
					return new Res<AdminDto>(ResultCodeEnum.IS_DELETED.getCode(), ResultCodeEnum.IS_DELETED.getMessage(), null);
				}else{
					AdminDto dto = BeanUtil.changeEntity(AdminDto.class, admin);
					return new Res<AdminDto>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), dto);
				}
			}else{
				return new Res<AdminDto>(ResultCodeEnum.PASSWORD_ERROR.getCode(), ResultCodeEnum.PASSWORD_ERROR.getMessage(), null);
			}
		}
		return new Res<AdminDto>(ResultCodeEnum.ACCOUNT_NOT_EXIST.getCode(), ResultCodeEnum.ACCOUNT_NOT_EXIST.getMessage(), null);
	}

	@Override
	public List<AdminListDto> adminList(AdminDataTablesReq req) {
		// TODO Auto-generated method stub
		return adminDao.adminList(req);
	}

	@Override
	public int adminCount(AdminDataTablesReq req) {
		// TODO Auto-generated method stub
		return adminDao.adminCount(req);
	}

	@Override
	public Res<Integer> addAdmin(AdminReq req) {
		// TODO Auto-generated method stub
		TAdmin admin = adminDao.adminDetailByAccount(req.getAdminAccount());
		if(admin!=null){
			return new Res<Integer>(ResultCodeEnum.ACCOUNT_IS_EXISTED.getCode(), ResultCodeEnum.ACCOUNT_IS_EXISTED.getMessage(), 0);
		}
		int result = adminDao.addAdmin(req);
		if(result!=0){
			return new Res<Integer>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), result);
		}
		return new Res<Integer>(ResultCodeEnum.SYSTEM_ERROR.getCode(), ResultCodeEnum.SYSTEM_ERROR.getMessage(), result);
	}

	@Override
	public Res<Integer> modifyAdmin(AdminReq req) {
		// TODO Auto-generated method stub
//		TAdmin admin = adminDao.adminDetailByAccount(req.getAdminAccount());
//		if(admin!=null && !admin.getAdminCode().equals(req.getAdminCode())){
//			return new Res<Integer>(ResultCodeEnum.ACCOUNT_IS_EXISTED.getCode(), ResultCodeEnum.ACCOUNT_IS_EXISTED.getMessage(), 0);
//		}
		int result = adminDao.modifyAdmin(req);
		if(result!=0){
			return new Res<Integer>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), result);
		}
		return new Res<Integer>(ResultCodeEnum.SYSTEM_ERROR.getCode(), ResultCodeEnum.SYSTEM_ERROR.getMessage(), result);
	
	}

	@Override
	public Res<Integer> delAdmin(String adminCodes) {
		// TODO Auto-generated method stub
		int delNum = 0;
		String[] code = adminCodes.split(",");
		for (int i = 0; i < code.length; i++) {
			int result = adminDao.delAdmin(code[i]);
			if(result!=0){
				delNum++;
			}else{
				return new Res<Integer>(ResultCodeEnum.SYSTEM_ERROR.getCode(), ResultCodeEnum.SYSTEM_ERROR.getMessage(), delNum);
			}
		}
		return new Res<Integer>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), delNum);
	}

	@Override
	public Res<AdminDto> adminDetail(String adminCode) {
		// TODO Auto-generated method stub
		TAdmin admin = adminDao.adminDetailByCode(adminCode);
		if(admin!=null){
			AdminDto dto = BeanUtil.changeEntity(AdminDto.class, admin);
			return new Res<AdminDto>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), dto);
		}
		return new Res<AdminDto>(ResultCodeEnum.ACCOUNT_NOT_EXIST.getCode(), ResultCodeEnum.ACCOUNT_NOT_EXIST.getMessage(), null);
	}

	@Override
	public Res<Integer> modifyAdminPwd(String adminCode, String adminPwd, String adminOldPwd) {
		// TODO Auto-generated method stub
		TAdmin admin = adminDao.adminDetailByCode(adminCode);
		if(admin!=null){
			if(admin.getAdminPwd().equals(adminOldPwd)){
				int result = adminDao.modifyAdminPwd(adminCode, adminPwd);
				if(result!=0){
					return new Res<Integer>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), result);
				}else{
					return new Res<Integer>(ResultCodeEnum.SYSTEM_ERROR.getCode(), ResultCodeEnum.SYSTEM_ERROR.getMessage(), result);
				}
			}else{
				return new Res<Integer>(ResultCodeEnum.PASSWORD_ERROR.getCode(), "原密码错误", 0);
			}
		}else{
			return new Res<Integer>(ResultCodeEnum.ACCOUNT_NOT_EXIST.getCode(), ResultCodeEnum.ACCOUNT_NOT_EXIST.getMessage(), 0);
		}
	}

	@Override
	public Res<Integer> resetAdminPwd(String adminCode) {
		// TODO Auto-generated method stub
		int result = adminDao.resetAdminPwd(adminCode);
		if(result!=0){
			return new Res<Integer>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), result);
		}
		return new Res<Integer>(ResultCodeEnum.SYSTEM_ERROR.getCode(), ResultCodeEnum.SYSTEM_ERROR.getMessage(), result);
	}
	

}
