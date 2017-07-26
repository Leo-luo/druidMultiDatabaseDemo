package com.common;

public interface Constants {

    String CONTENT_TYPE_APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";
    
    /**
     * 数据字典:基础数据标识
     */
    String DICT_DATABASE = "dict-database";
    
    /**
     * 数据库状态:1:正常 3:删除
     */
    int DATA_STATUS_DELETE = 3;
    int DATA_STATUS_NORMAL = 1;
    
    /**
     * 数据库编码规则:固定编码+时间戳
     */
    String ADMIN_CODE = "AC";//管理员账号编码
    String MODULE_CODE = "MC";//模块编码
    String DICT_CODE = "DC";//数据字典编码
    String ROLE_CODE = "RC";//角色编码
    String USER_CODE = "UC";//用户编码
    String CONTRACT_CODE = "CC";//联系人编码
    String CUSTOMER_CODE = "CUS";//需要服务人编码
    String CUS_HEALTH_CONDITION_CODE = "HCC";//需要服务人:健康状况编码
    String CUS_LBH_CODE = "LBH";//需要服务人:生活与行为习惯编码
    String CUS_NURSE_CONDITION_CODE = "CNC";//需要服务人:照护情况编码
    String ORG_CODE = "OC";//机构编码
    String PROFESSIONAL_CODE = "PC";//专业人员编码
    String INFORMATION_CODE = "IC";//资讯编码
    
}
