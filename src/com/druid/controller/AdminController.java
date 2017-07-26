package com.druid.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.JSONResponse;
import com.common.PublicService;
import com.common.Res;
import com.common.ResultCodeEnum;
import com.druid.bean.dto.AdminDto;
import com.druid.bean.dto.AdminListDto;
import com.druid.bean.req.AdminDataTablesReq;
import com.druid.bean.req.AdminReq;
import com.druid.service.AdminService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/admin")
@Api(value = "管理员", description = "管理员相关接口")
public class AdminController {
	@Autowired
	private PublicService publicService;
	@Autowired
	private AdminService adminService;
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/login")
    @ApiOperation(value = "登陆", notes = "登陆")
	public Res<AdminDto> adminLogin(
			@ApiParam(required = true, value = "账号")@RequestParam String adminAccount,
			@ApiParam(required = true, value = "密码")@RequestParam String adminPwd){
		/**必填参数非空检验
		if(!publicService.checkParam(adminAccount, adminPwd)){
			return new Res<AdminDto>(ResultCodeEnum.PARAMETER_NULL.getCode(), ResultCodeEnum.PARAMETER_NULL.getMessage(), null);
		}*/
		return adminService.adminLogin(adminAccount, adminPwd);
	}

	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/select_pagination")
    @ApiOperation(value = "管理员列表", notes = "分页")
	public JSONResponse<AdminListDto> adminList(@ModelAttribute AdminDataTablesReq req){
		req.setStart(0);
		req.setLength(2);
		List<AdminListDto> dto = adminService.adminList(req);
		req.setStart(2);
		req.setLength(2);
		List<AdminListDto> dto2 = adminService.adminList(req);
		List<AdminListDto> returnDto = new ArrayList<AdminListDto>();
		if(dto!=null){
			for (int i = 0; i < dto.size(); i++) {
				returnDto.add(dto.get(i));
			}
		}
		if(dto2!=null){
			for (int i = 0; i < dto2.size(); i++) {
				returnDto.add(dto2.get(i));
			}
		}
		JSONResponse<AdminListDto> jsonResponse = new JSONResponse<AdminListDto>(ResultCodeEnum.OPERATION_SUCCESS.getCode(), ResultCodeEnum.OPERATION_SUCCESS.getMessage(), 0, 0, new ArrayList<AdminListDto>());
        if(dto!=null){
        	
        	int count = returnDto.size();
        	int totalCount = adminService.adminCount(req);
        	jsonResponse.setRecords(count);
            jsonResponse.setTotalRecords(totalCount);
            jsonResponse.setDataList(returnDto);
        }
		return jsonResponse;
	}
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @ApiOperation(value = "新增", notes = "新增")
	public Res<Integer> addAdmin(@RequestBody AdminReq req){
		/**必填参数非空检验
		if(!publicService.checkParam(adminAccount, adminPwd)){
			return new Res<AdminDto>(ResultCodeEnum.PARAMETER_NULL.getCode(), ResultCodeEnum.PARAMETER_NULL.getMessage(), null);
		}*/
		return adminService.addAdmin(req);
	}
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/modify")
	@ApiOperation(value = "修改", notes = "编码必填")
	public Res<Integer> modifyAdmin(@RequestBody AdminReq req){
		/**必填参数非空检验*/
		if(!publicService.checkParam(req.getAdminCode())){
			return new Res<Integer>(ResultCodeEnum.PARAMETER_NULL.getCode(), ResultCodeEnum.PARAMETER_NULL.getMessage(), null);
		}
		return adminService.modifyAdmin(req);
	}
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/del")
	 @ApiOperation(value = "删除", notes = "可批量")
	public Res<Integer> delAdmin(@ApiParam(required = true, value = "编码串','逗号隔开")@RequestParam String adminCodes){
		/**必填参数非空检验*/
		if(!publicService.checkParam(adminCodes)){
			return new Res<Integer>(ResultCodeEnum.PARAMETER_NULL.getCode(), ResultCodeEnum.PARAMETER_NULL.getMessage(), null);
		}
		return adminService.delAdmin(adminCodes);
	}
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/detail")
    @ApiOperation(value = "详情", notes = "编码必填")
	public Res<AdminDto> adminDetail(@ApiParam(required = true, value = "编码")@RequestParam String adminCode){
		/**必填参数非空检验*/
		if(!publicService.checkParam(adminCode)){
			return new Res<AdminDto>(ResultCodeEnum.PARAMETER_NULL.getCode(), ResultCodeEnum.PARAMETER_NULL.getMessage(), null);
		}
		return adminService.adminDetail(adminCode);
	}
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/modify_pwd")
	 @ApiOperation(value = "修改密码", notes = "修改密码")
	public Res<Integer> modifyAdminPwd(
			@ApiParam(required = true, value = "编码")@RequestParam String adminCode,
			@ApiParam(required = true, value = "新密码")@RequestParam String adminPwd,
			@ApiParam(required = true, value = "旧密码")@RequestParam String adminOldPwd){
		/**必填参数非空检验*/
		if(!publicService.checkParam(adminCode, adminPwd, adminOldPwd)){
			return new Res<Integer>(ResultCodeEnum.PARAMETER_NULL.getCode(), ResultCodeEnum.PARAMETER_NULL.getMessage(), null);
		}
		return adminService.modifyAdminPwd(adminCode, adminPwd, adminOldPwd);
	}
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/reset_pwd")
	 @ApiOperation(value = "重置密码", notes = "重置密码")
	public Res<Integer> resetAdminPwd(@ApiParam(required = true, value = "编码")@RequestParam String adminCode){
		/**必填参数非空检验*/
		if(!publicService.checkParam(adminCode)){
			return new Res<Integer>(ResultCodeEnum.PARAMETER_NULL.getCode(), ResultCodeEnum.PARAMETER_NULL.getMessage(), null);
		}
		return adminService.resetAdminPwd(adminCode);
	}
}
