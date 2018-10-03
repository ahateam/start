package xhj.cn.start.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

@RDSAnnEntity(alias = "tb_user_message")
public class User {
	
	/**
	 * 用户客户端ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long user_id;
	
	/**
	 * 用户客访问公众号openID
	 */
	@RDSAnnID
	@RDSAnnField(column = "VARCHAR(64)")
	public String user_openId;
	
	/**
	 * 用户对应用户名称
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String user_name;
	
	/**
	 * 用户对应性别
	 */
	@RDSAnnField(column = "VARCHAR(2)")
	public String user_sex;
	
	/**
	 * 用户对应所在城市
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String user_city;
	
	/**
	 * 用户对应所在省份
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String user_province;
	
	/**
	 * 用户对应所在国家
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String user_country;
	
	/**
	 * 用户关注时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date user_creatTime;
	
	/**
	 * 用户标签列表
	 */
	@RDSAnnField(column = RDSAnnField.JSON)
	public String user_tagList;

}
