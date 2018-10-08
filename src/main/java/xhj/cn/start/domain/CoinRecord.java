package xhj.cn.start.domain;

import javax.xml.crypto.Data;

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

	/**
	 * 记录ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;
	
	/**
	 * 对应用户ID
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;

	/**
	 * 操作类型
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte operation;

	/**
	 * 操作数值
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer count;

	/**
	 * 余额
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer remainingAmount;
	
	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Data createTime;
}
