package com.druid.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.common.Constants;
import com.druid.bean.dto.AdminListDto;
import com.druid.bean.req.AdminDataTablesReq;
import com.utils.DateUtils;

@Component 
public class TestDaoImplRead {
	 private static Logger logger = Logger.getLogger(TestDaoImplRead.class);
	 
	 private JdbcTemplate jdbcTemplateRead;

     @Autowired
     public void setJdbcTemplateRead(@Qualifier("dataSourceRead") DataSource dataSource) {
        this.jdbcTemplateRead = new JdbcTemplate(dataSource);
     }
	 
	 /**
	 * 管理员列表
	 * @param AdminDataTablesReq
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<AdminListDto> adminList(AdminDataTablesReq req){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.*, b.role_name,  ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_connect) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleConnects, ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_code) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleCodes, ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_name) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleNames ");
			sql.append(" FROM t_yh_admin a ");
			sql.append(" LEFT JOIN t_yh_role b ON b.role_code=a.role_code ");
			sql.append(" WHERE a.admin_status!="+Constants.DATA_STATUS_DELETE);
			
			//查询条件
			if(req.getAdminName()!=null && !"".equals(req.getAdminName())){
				sql.append(" and a.admin_name like '%"+req.getAdminName()+"%' ");
			}
			if(req.getAdminAccount()!=null && !"".equals(req.getAdminAccount())){
				sql.append(" and a.admin_account like '%"+req.getAdminAccount()+"%' ");
			}
			if(req.getStartTime()!=null && !"".equals(req.getStartTime()) && req.getEndTime()!=null && !"".equals(req.getEndTime())){
				sql.append(" and a.create_time>='"+DateUtils.fromDateToFormatString(req.getStartTime(), "yyyy-MM-dd")+" 00:00:00'");
				sql.append(" and a.create_time<='"+DateUtils.fromDateToFormatString(req.getEndTime(), "yyyy-MM-dd")+" 23:59:59'");
			}
			
			sql.append(" ORDER BY a.create_time DESC ");
			sql.append(" limit ").append(req.getStart()).append(",").append(req.getLength());
			logger.info("TestDaoImplRead.adminList sql = " + sql.toString());
		    return jdbcTemplateRead.query(sql.toString(), new BeanPropertyRowMapper(AdminListDto.class));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 管理员列表长度
	 * @param AdminDataTablesReq
	 * @return
	 */
	public int adminCount(AdminDataTablesReq req){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT count(a.admin_id) FROM t_yh_admin a ");
			sql.append(" WHERE a.admin_status!="+Constants.DATA_STATUS_DELETE);
			
			//查询条件
			if(req.getAdminName()!=null && !"".equals(req.getAdminName())){
				sql.append(" and a.admin_name like '%"+req.getAdminName()+"%' ");
			}
			if(req.getAdminAccount()!=null && !"".equals(req.getAdminAccount())){
				sql.append(" and a.admin_account like '%"+req.getAdminAccount()+"%' ");
			}
			if(req.getStartTime()!=null && !"".equals(req.getStartTime()) && req.getEndTime()!=null && !"".equals(req.getEndTime())){
				sql.append(" and a.create_time>='"+DateUtils.fromDateToFormatString(req.getStartTime(), "yyyy-MM-dd")+" 00:00:00'");
				sql.append(" and a.create_time<='"+DateUtils.fromDateToFormatString(req.getEndTime(), "yyyy-MM-dd")+" 23:59:59'");
			}
			
			logger.info("TestDaoImplRead.adminCount sql = " + sql.toString());
		    return jdbcTemplateRead.queryForInt(sql.toString());
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}
