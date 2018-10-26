package xhj.cn.custom.domain;

import java.util.Date;

/**
 * 以旧换新订单
 */
public class ChangeOrder {
	
	//执行途径
	public static final String WAY_VISIT = "上门服务";
	public static final String WAY_ARRIVE = "用户到店";
	
	//是否付款
	public static final Byte PAY_YES = 1;
	public static final Byte PAY_NO = 0;
	
	//订单状态
	public static final String REPAIR_OFF = "已取消";
	public static final String REPAIR_ON = "已完成";
	public static final String REPAIR_LOAD = "待处理";
	
	//订单类型
	public static final String TYPE_NEW = "以旧换新";
	public static final String TYPE_RECYCLE = "旧机回收";
	
    public Long id;
	
	/**
	 * 订单标题
	 */
	public String title;
	
	/**
	 * 用户ID
	 */
	public Long userId;
	
	/**
	 * 用户姓名
	 */
	public String userName;
	
	/**
	 * 用户电话
	 */
	public String userPhone;
	
	/**
	 * 订单类型
	 */
	public String type;
	
	/**
	 * 手机品牌
	 */
	public String brand;
	
	/**
	 * 手机型号
	 */
	public String version;
	
	/**
	 * 旧机描述（如使用时间、是否磨损等）
	 */
	public String phoneMessage;
	
	/**
	 * 旧机价值
	 */
	public Double oldPrice;
	
	/**
	 *新机对应商品ID
	 */
	public String phoneId;
	
	/**
	 * 更换新机（直接保存新机title）
	 */
	public String newPhone;
	
	/**
	 * 新机价格
	 */
	public Double newPrice;
	
	/**
	 * 执行途径
	 */
	public String changeWay;
	
	/**
	 * 地址
	 */
	public String address;
	
	/**
	 * 补差价格
	 */
	public Double minusPrice;
	
	/**
	 * 是否付款
	 */
	public Byte isPay;
	
	/**
	 * 付款方式
	 */
	public String payWay;
	
	/**
	 * 订单状态
	 */
	public String status;
	
	/**
	 * 工单处理人（ID）
	 */
	public Long handler;
	
	/**
	 * 创建时间
	 */
	public Date createTime;

}
