package com.druid.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.JSONResponse;
import com.common.ResultCodeEnum;
import com.druid.bean.dto.AdminListDto;
import com.druid.bean.req.AdminDataTablesReq;
import com.druid.service.TestService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/test")
@Api(value = "测试", description = "多个数据源拿取数据")
public class TestController {
	@Autowired
	private TestService testService;

	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/select_pagination")
    @ApiOperation(value = "列表", notes = "分页")
	public JSONResponse<AdminListDto> adminList(@ModelAttribute AdminDataTablesReq req){
		List<AdminListDto> dto = testService.adminList(req);
		JSONResponse<AdminListDto> jsonResponse = new JSONResponse<AdminListDto>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), 0, 0, new ArrayList<AdminListDto>());
        if(dto!=null){
        	
        	int count = dto.size();
        	int totalCount = testService.adminCount(req);
        	jsonResponse.setRecords(count);
            jsonResponse.setTotalRecords(totalCount);
            jsonResponse.setDataList(dto);
        }
		return jsonResponse;
	}
}
