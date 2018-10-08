package xhj.cn.start.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;
/**
 * 订单
 *
 */
@RDSAnnEntity(alias = "tb_order")
public class Order {
	
	public static final Byte SEND_ON = 1;
	public static final Byte SEND_OFF = 0;
	public static final Byte PAY_ON = 1;
	public static final Byte PAY_OFF = 0;
	/**
	 * 订单ID
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
	 * 标题
	 */
	@RDSAnnField(column = "VARCHAR(128)")
	public String title;
	/**
	 * 物品花费价格
	 */
	@RDSAnnField(column = RDSAnnField.DOUBLE)
	public Double price;
	/**
	 * 物品花费积分
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer coin;
	/**
	 * 是否付款
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte pay;
	/**
	 * 是否发货
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte send;
	/**
	 * 订单发货地址
	 */
	@RDSAnnField(column = "VARCHAR(128)")
	public String address;
	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date creatTime;
	/**
	 * 对应积分事件记录ID
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long coinrecId;

}
