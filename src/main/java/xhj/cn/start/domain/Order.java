package xhj.cn.start.domain;

import java.util.Date;
/**
 * 订单
 *
 */
public class Order {
	
	public static final Byte SEND_ON = 1;
	public static final Byte SEND_OFF = 0;
	public static final Byte PAY_ON = 1;
	public static final Byte PAY_OFF = 0;
	/**
	 * 订单ID
	 */
	public Long id;
	/**
	 * 对应用户ID
	 */
	public Long userId;
	/**
	 * 标题
	 */
	public String title;
	/**
	 * 物品花费价格
	 */
	public Double price;
	/**
	 * 物品花费积分
	 */
	public Integer coin;
	/**
	 * 是否付款
	 */
	public Byte pay;
	/**
	 * 是否发货
	 */
	public Byte send;
	/**
	 * 创建时间
	 */
	public Date creatTime;
	/**
	 * 对应积分事件记录ID
	 */
	public Long coinrecId;

}
