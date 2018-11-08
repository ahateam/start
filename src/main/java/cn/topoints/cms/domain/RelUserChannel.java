package cn.topoints.cms.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 用户与专栏的关系表（关注）
 *
 */
@RDSAnnEntity(alias = "tb_rel_user_channel")
public class RelUserChannel {

	/**
	 * 用户编号，ID列，无需索引
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;

	/**
	 * 频道编号，ID列，无需索引
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long channelId;

	/**
	 * 关注时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date createTime;

	/**
	 * 上次同步时间(设为已读后，此时间将跟新)
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date lastSyncTime;


}
