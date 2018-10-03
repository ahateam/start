package cn.topoints.core.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 用户Session，使用OTS存储</br>
 * 缓存有效期30分钟，OTS存储有效期2天
 */
@RDSAnnEntity(alias = "tb_user_session")
public class UserSession {

	/**
	 * 用户编号
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;

	/**
	 * 此列只是记录，并不是ID
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 用户等级
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte level;

	/**
	 * 登录时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date loginTime;

	/**
	 * 登录令牌
	 */
	@RDSAnnField(column = "VARCHAR(256)")
	public String loginToken;

}
