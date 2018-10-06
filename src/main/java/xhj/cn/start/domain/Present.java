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
	
	/**
	 * 礼品ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long presentId;
	/**
	 * 礼品名称
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String presentName;
	/**
	 * 礼品描述
	 */
	@RDSAnnField(column = RDSAnnField.TEXT)
	public String presentMessage;
	/**
	 * 礼品类型
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String presentType;
	/**
	 * 礼品有效时间（始）
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Data presentStartTime;
	/**
	 * 礼品有效时间（终）
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Data presentEndTime;
	/**
	 * 礼品库存
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer presentRepertory;
	/**
	 * 虚拟（1）/实物（0）
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer presentDummy;

}
