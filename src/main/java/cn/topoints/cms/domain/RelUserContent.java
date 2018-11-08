package cn.topoints.cms.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 用户与内容的关系表（创建发布）
 *
 */
@RDSAnnEntity(alias = "tb_rel_user_content")
public class RelUserContent {

	/**
	 * 用户编号，ID列，无需索引
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;

	/**
	 * 内容编号，ID列，无需索引
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long contentId;

	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date createTime;

}
