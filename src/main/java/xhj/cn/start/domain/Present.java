package xhj.cn.start.domain;

import javax.xml.crypto.Data;

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
	public Data createTime;

	/**
	 * 有效时间（始）
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Data startTime;
	/**
	 * 有效时间（终）
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Data endTime;

	/**
	 * 礼品库存
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer amount;

	/**
	 * 礼品库存
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer remainingAmount;

	/**
	 * 牛逼的JSON</br>
	 * store，对应门店信息</br>
	 * type，对应分类信息（虚拟，实物，落地页）</br>
	 */
	@RDSAnnField(column = RDSAnnField.JSON)
	public String tags;
}
