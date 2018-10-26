package xhj.cn.custom.domain;

import java.util.Date;

/*
 * 退换订单
 */
public class RetreatOrder {
	
	//执行途径
	public static final String WAY_VISIT = "上门服务";
	public static final String WAY_ARRIVE = "用户到店";
	
	//订单状态
	public static final String RETREAT_OFF = "已取消";
	public static final String RETREAT_ON = "已退换";
	public static final String RETREAT_LOAD = "审核中";
	
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
	 * 手机品牌
	 */
	public String brand;
	
	/**
	 * 手机型号
	 */
	public String version;
	
	/**
	 * 购买该手机订单号
	 */
	public String buyId;
	
	/**
	 * 退换原因描述
	 */
	public String retreatMessage;
	
	/**
	 * 执行途径
	 */
	public String changeWay;
	
	/**
	 * 地址
	 */
	public String address;
	
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
