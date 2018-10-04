package cn.topoints.core.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;
import cn.topoints.utils.data.rds.RDSAnnIndex;

/**
 * 管理员表
 */
@RDSAnnEntity(alias = "tb_admin")
public class Admin {


	/**
	 * 用户编号
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	/**
	 * 所属app的编号</br>
	 * (内容很多，最好建索引)
	 */
	@RDSAnnIndex
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date createDate;

	/**
	 * 管理员等级</br>
	 * 目前只分基本用户（BASIC）和会员用户（MEMBER）两种
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte level;

	/**
	 * 管理员名称
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String name;

	/**
	 * 手机号
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String mobile;

	/**
	 * 邮箱
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String email;

	/**
	 * QQ开放平台id(索引)
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String qqOpenId;

	/**
	 * 微信开放平台id(索引)
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String wxOpenId;

	/**
	 * 状态
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte status;

	/**
	 * 密码
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String pwd;

	/**
	 * 昵称
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String nickname;

}
