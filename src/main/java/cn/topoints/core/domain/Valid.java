package cn.topoints.core.domain;

import cn.topoints.utils.data.ots.OTSAnnEntity;
import cn.topoints.utils.data.ots.OTSAnnField;
import cn.topoints.utils.data.ots.OTSAnnID;

@OTSAnnEntity(alias = "valid")
public class Valid {

	@OTSAnnID(keyType = OTSAnnID.KeyType.PARTITION_KEY)
	@OTSAnnField(column = OTSAnnField.ColumnType.INTEGER)
	public Long id;

	/**
	 * 有效时间，单位，分钟
	 */
	@OTSAnnField(column = OTSAnnField.ColumnType.INTEGER)
	public Integer expire;

	/**
	 * 验证码，一般是4位或6位随机数
	 */
	@OTSAnnField(column = OTSAnnField.ColumnType.STRING)
	public String code;

}
