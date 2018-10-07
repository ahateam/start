package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 金币（积分消费记录）
 *
 */
public class CoinRecord {

	public static final Byte OPERATION_ADD = 0;
	public static final Byte OPERATION_SUB = 1;
	public static final Byte OPERATION_SET = 2;

	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte operation;

	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer count;

	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer remainingAmount;
}
