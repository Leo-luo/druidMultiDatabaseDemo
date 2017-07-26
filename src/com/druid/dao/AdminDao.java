package com.druid.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.common.Constants;
import com.druid.bean.TAdmin;
import com.druid.bean.dto.AdminListDto;
import com.druid.bean.req.AdminDataTablesReq;
import com.druid.bean.req.AdminReq;
import com.utils.DateUtils;

@Component
public class AdminDao {
	private static Logger logger = Logger.getLogger(AdminDao.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setJdbcTemplate(@Qualifier("dataSourceWrite") DataSource dataSource) {
       this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	/**
	 * 管理员信息
	 * @param adminAccount
	 * @return
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TAdmin adminDetailByAccount(String adminAccount){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.*, b.role_name, ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_connect) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleConnects, ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_code) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleCodes, ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_name) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleNames ");
			sql.append(" FROM t_yh_admin a ");
			sql.append(" LEFT JOIN t_yh_role b ON b.role_code=a.role_code ");
			sql.append(" WHERE a.`admin_account`='"+adminAccount+"'");
			logger.info("AdminDao.adminDetailByAccount sql = " + sql.toString());
			return (TAdmin)jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper(TAdmin.class));
		}catch(Exception e){
//			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 管理员信息
	 * @param adminCode
	 * @return
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TAdmin adminDetailByCode(String adminCode){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.*, b.role_name, ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_connect) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleConnects, ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_code) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleCodes, ");
			sql.append(" (SELECT GROUP_CONCAT(d.module_name) FROM t_yh_role_mod c, t_yh_module d WHERE c.role_code=a.`role_code` AND c.module_code=d.module_code AND d.module_status!="+Constants.DATA_STATUS_DELETE+" GROUP BY c.role_code) AS moduleNames ");
			sql.append(" FROM t_yh_admin a ");
			sql.append(" LEFT JOIN t_yh_role b ON b.role_code=a.role_code ");
			sql.append(" WHERE a.`admin_code`='"+adminCode+"'");
			logger.info("AdminDao.adminDetailByCode sql = " + sql.toString());
			return (TAdmin)jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper(TAdmin.class));
		}catch(Exception e){
//			e.printStackTrace();
			return null;
		}
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
			logger.info("AdminDao.adminList sql = " + sql.toString());
		    return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(AdminListDto.class));
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
			
			logger.info("AdminDao.adminCount sql = " + sql.toString());
		    return jdbcTemplate.queryForInt(sql.toString());
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 新增管理员账号
	 * @param AdminReq
	 * @return
	*/
	public int addAdmin(final AdminReq req){
		try{
			if(req == null){
				return 0;
			}
			StringBuffer sql = new StringBuffer();
			sql.append("insert into t_yh_admin (admin_code,admin_account,admin_pwd,admin_status,create_time,role_code,admin_type,admin_name,admin_phone ");
			sql.append(" ) values ( ");
			sql.append("?,?,?,?,?,?,?,?,?)");
			logger.info("AdminDao.addAdmin sql = " + sql.toString());
			final String sqlStr = sql.toString();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator(){
				public PreparedStatement createPreparedStatement(Connection conn)  
			         throws SQLException {  
			         PreparedStatement ps = conn.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS); 
			         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			         SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			       	 String date = sdf.format(new Date());
			       	 String dateCode = sdf2.format(new Date());
			         ps.setString(1, Constants.ADMIN_CODE+dateCode);
			         ps.setString(2, req.getAdminAccount());
			         ps.setString(3, req.getAdminPwd());
			         ps.setInt(4, Constants.DATA_STATUS_NORMAL);
			         ps.setString(5, date);
			         ps.setString(6, req.getRoleCode());
			         ps.setInt(7, req.getAdminType());
			         ps.setString(8, req.getAdminName());
			         ps.setString(9, req.getAdminPhone());
			         return ps;
			     }
				}, keyHolder);
			int pkid = keyHolder.getKey().intValue();
			return pkid;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 修改管理员账号
	 * @param AdminReq
	 */
	public int modifyAdmin(AdminReq req){
		StringBuffer sql = new StringBuffer();
		try{
			sql.append("update t_yh_admin set admin_name=?,role_code=?,admin_phone=?,admin_type=? where admin_code='").append(req.getAdminCode()).append("'");
			logger.info("AdminDao.modifyAdmin sql = " + sql.toString());
			return jdbcTemplate.update(sql.toString(), new Object[]{req.getAdminName(), req.getRoleCode(), req.getAdminPhone(), req.getAdminType()});
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 逻辑删除管理员账号
	 * @param adminCode
	 * @return
	 */
	public int delAdmin(String adminCode){
		StringBuffer sql = new StringBuffer();
		try{
			sql.append("update t_yh_admin set admin_status="+Constants.DATA_STATUS_DELETE);
			sql.append(" where admin_code='"+adminCode+"'");
			logger.info("AdminDao.delAdmin sql = " + sql.toString());
			return jdbcTemplate.update(sql.toString());
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 修改管理员密码
	 * @param adminCode
	 * @param adminPwd
	 */
	public int modifyAdminPwd(String adminCode, String adminPwd){
		StringBuffer sql = new StringBuffer();
		try{
			sql.append("update t_yh_admin set admin_pwd=? where admin_code='").append(adminCode).append("'");
			logger.info("AdminDao.modifyAdmin sql = " + sql.toString());
			return jdbcTemplate.update(sql.toString(), new Object[]{adminPwd});
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 重置管理员密码
	 * @param adminCode
	 */
	public int resetAdminPwd(String adminCode){
		StringBuffer sql = new StringBuffer();
		try{
			sql.append("update t_yh_admin set admin_pwd=? where admin_code='").append(adminCode).append("'");
			logger.info("AdminDao.resetAdminPwd sql = " + sql.toString());
			return jdbcTemplate.update(sql.toString(), new Object[]{"123456"});
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
}
