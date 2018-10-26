package xhj.cn.custom.domain;

import java.util.Date;

/*
 * 保险订单
 */
public class InsuranceOrder {
	
	//是否付款
	public static final Byte PAY_YES = 1;
	public static final Byte PAY_NO = 0;
		
	//订单状态
	public static final String REPAIR_OFF = "已取消";
	public static final String REPAIR_ON = "已维修";
	public static final String REPAIR_LOAD = "待处理";
		
	//支付方式
	public static final String PAY_ON = "线上支付";
	public static final String PAY_DOWN = "线下支付";
	
    public Long id;
	
	/**
	 * 订单标题
	 */
	public String title;
	
	/**
	 * 购保类型
	 */
	public String type;
	
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
	 * 价格
	 */
	public Double price;
	
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
