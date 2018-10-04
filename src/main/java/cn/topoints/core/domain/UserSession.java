package cn.topoints.core.domain;

import java.util.Date;

import cn.topoints.utils.data.ots.OTSAnnEntity;
import cn.topoints.utils.data.ots.OTSAnnField;
import cn.topoints.utils.data.ots.OTSAnnID;

/**
 * 用户Session，使用OTS存储</br>
 * 缓存有效期30分钟，OTS存储有效期2天
 */
@OTSAnnEntity(alias = "useSession")
public class UserSession {

	/**
	 * 此列只是记录，并不是ID</br>
	 * 分片键
	 */
	@OTSAnnID(keyType = OTSAnnID.KeyType.PARTITION_KEY)
	@OTSAnnField(column = OTSAnnField.ColumnType.INTEGER)
	public Long appId;

	/**
	 * 用户编号
	 */
	@OTSAnnID(keyType = OTSAnnID.KeyType.PRIMARY_KEY_1)
	@OTSAnnField(column = OTSAnnField.ColumnType.INTEGER)
	public Long userId;

	/**
	 * 用户等级
	 */
	@OTSAnnField(column = OTSAnnField.ColumnType.INTEGER)
	public Byte level;

	/**
	 * 登录时间
	 */
	@OTSAnnField(column = OTSAnnField.ColumnType.INTEGER)
	public Date loginTime;

	/**
	 * 登录令牌
	 */
	@OTSAnnField(column = OTSAnnField.ColumnType.STRING)
	public String loginToken;

}
