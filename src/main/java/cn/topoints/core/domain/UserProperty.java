package cn.topoints.core.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 用户已购买的内容资产
 *
 */
@RDSAnnEntity(alias = "tb_user_property")
public class UserProperty {

	public static final Byte TYPE_CONTENT = 0;
	public static final Byte TYPE_SET = 1;

	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;

	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long propertyId;

	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte type;

	// 以下是冗余信息
}
