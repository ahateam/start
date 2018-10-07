package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;
import cn.topoints.utils.data.rds.RDSAnnIndex;

/**
 * 分销员，最多支持三级分销
 *
 */
@RDSAnnEntity(alias = "tb_distributor")
public class Distributor {

	public static final Byte LEVEL_1 = 1;
	public static final Byte LEVEL_2 = 2;
	public static final Byte LEVEL_3 = 3;

	/**
	 * 分销员ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	/**
	 * 所属app的编号</br>
	 * (最好建索引)
	 */
	@RDSAnnIndex
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 分销员等级，只支持3级
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte level;

	/**
	 * 上级分销员编号</br>
	 * 带索引
	 */
	@RDSAnnIndex
	@RDSAnnField(column = RDSAnnField.ID)
	public Long leaderId;

	@RDSAnnField(column = "VARCHAR(32)")
	public String name;
}
