package xhj.cn.start.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 礼品
 *
 */
@RDSAnnEntity(alias = "tb_present")
public class Present {

	public static final Byte TYPE_VIRTUAL = 0;
	public static final Byte TYPE_OBJECT = 1;

	/**
	 * 礼品ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	/**
	 * 标题
	 */
	@RDSAnnField(column = "VARCHAR(128)")
	public String title;

	/**
	 * 描述
	 */
	@RDSAnnField(column = RDSAnnField.SHORT_TEXT)
	public String discription;

	/**
	 * 状态
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte status;

	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date createTime;

	/**
	 * 有效时间（始）
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date startTime;
	/**
	 * 有效时间（终）
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date endTime;

	/**
	 * 礼品库存
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer amount;

	/**
	 * 礼品剩余库存
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer remainingAmount;

	/**
	 * 牛逼的JSON</br>
	 */
	@RDSAnnField(column = RDSAnnField.JSON)
	public String tags;
}
