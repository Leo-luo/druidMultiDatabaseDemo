package com.druid.service;

import java.util.List;

import com.druid.bean.dto.AdminListDto;
import com.druid.bean.req.AdminDataTablesReq;

public interface TestService {
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
}
