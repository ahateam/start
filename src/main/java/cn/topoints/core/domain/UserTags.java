package cn.topoints.core.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;
import cn.topoints.utils.data.rds.RDSAnnIndex;

@RDSAnnEntity(alias = "tb_user_tags")
public class UserTags {

	/**
	 * 用户编号
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;

	/**
	 * 所属app的编号</br>
	 * (内容很多，最好建索引)
	 */
	@RDSAnnIndex
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 牛逼的JSON
	 */
	@RDSAnnField(column = RDSAnnField.JSON)
	public String tags;
}
