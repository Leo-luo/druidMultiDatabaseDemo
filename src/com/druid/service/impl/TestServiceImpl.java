package com.druid.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.druid.bean.dto.AdminListDto;
import com.druid.bean.req.AdminDataTablesReq;
import com.druid.dao.impl.TestDaoImplRead;
import com.druid.dao.impl.TestDaoImplWrite;
import com.druid.service.TestService;
@Service
public class TestServiceImpl implements TestService {
	@Autowired
	private TestDaoImplRead testDaoImplRead;
	@Autowired
	private TestDaoImplWrite testDaoImplWrite;
	
	@Override
	public List<AdminListDto> adminList(AdminDataTablesReq req) {
		// TODO Auto-generated method stub
		List<AdminListDto> returnList = new ArrayList<AdminListDto>();
		List<AdminListDto> dtoa = testDaoImplRead.adminList(req);
		List<AdminListDto> dtob = testDaoImplWrite.adminList(req);
		if(dtoa!=null){
			for (int i = 0; i < dtoa.size(); i++) {
				returnList.add(dtoa.get(i));
			}
		}
		if(dtob!=null){
			for (int i = 0; i < dtob.size(); i++) {
				returnList.add(dtob.get(i));
			}
		}
		return returnList;
	}
	@Override
	public int adminCount(AdminDataTablesReq req) {
		// TODO Auto-generated method stub
		return testDaoImplRead.adminCount(req);
	}

}
