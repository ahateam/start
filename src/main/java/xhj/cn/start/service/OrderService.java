package xhj.cn.start.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.IDUtils;
import cn.topoints.utils.api.ServerException;
import xhj.cn.start.domain.CoinRecord;
import xhj.cn.start.domain.Order;
import xhj.cn.start.repository.CoinRecordRepository;
import xhj.cn.start.repository.CoinRepository;
import xhj.cn.start.repository.OrderRepository;
import xhj.cn.start.util.GetDomain;

public class OrderService {
	
	private static Logger log = LoggerFactory.getLogger(OrderService.class);

	private static OrderService ins;

	public static synchronized OrderService getInstance() {
		if (null == ins) {
			ins = new OrderService();
		}
		return ins;
	}

	private CoinRepository coinRepository;
	private CoinRecordRepository coinRecordRepository;
	private OrderRepository orderRepository;
	private GetDomain gd = new GetDomain();

	private OrderService() {
		try {
			coinRepository = CoinRepository.getInstance();
			coinRecordRepository = CoinRecordRepository.getInstance();
			orderRepository = OrderRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @描述 根据ID拉取订单信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Order getOrderById(DruidPooledConnection conn,Long id) throws Exception {
		return orderRepository.getOrderById(conn, id);
	}
	
	/**
	 * @描述 根据用户ID获取用户订单列表
	 * @param conn
	 * @param userId
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Order> getOrderListByUserId(DruidPooledConnection conn,Long userId,Integer count,Integer offset) throws Exception{
		return orderRepository.getOrderByUserid(conn, userId, count, offset);
	}
	
	/**
	 * @描述 获取（是/否）付款及（是/否）发货订单
	 * @param conn
	 * @param pay
	 * @param send
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Order> getOrderBySend(DruidPooledConnection conn,Byte pay,Byte send,Integer count,Integer offset) throws ServerException{
		return orderRepository.getOrderBySend(conn, pay, send, count, offset);
	}
	
	/**
	 * @描述 根据订单标题模糊查询订单列表
	 * @param conn
	 * @param title
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Order> getOrderLikeTitle(DruidPooledConnection conn,String title,Integer count,Integer offset) throws Exception{
		return orderRepository.getOrderLikeTitle(conn, title, count, offset);
	}
	
	/**
	 * @描述 添加订单并执行积分操作记录捆绑（功能暂未完成，后续完善）
	 * @param conn
	 * @param userId
	 * @param title
	 * @param price
	 * @param coin
	 * @param pay
	 * @throws Exception
	 */
	public void setOrderAndCoinRec(DruidPooledConnection conn,Long userId,String title,Double price,
			Integer coin,Byte pay,String province,String city,String address) throws Exception {
		Order o = new Order();
		CoinRecord c = new CoinRecord();
		
		//公共参数
		Long coinrecId = IDUtils.getSimpleId();
		
		//定义订单信息
		o.id = IDUtils.getSimpleId();
		o.userId = userId;
		o.title = title;
		o.price = price;
		o.coin = coin;
		o.pay = pay;
		o.province = province;
		o.city = city;
		o.address = address;
		o.send = Order.SEND_OFF;     //订单创建时默认为未发货状态
		o.creatTime = new Date();
		o.coinrecId = coinrecId;
		
		//定义积分事假信息
		c.id = coinrecId;
		c.userId = userId;
		c.operation = CoinRecord.OPERATION_ADD; //该项操作参数暂未决定
		c.count = 0;                            //该项操作参数暂未决定
		c.remainingAmount = 0;                  //该项操作参数暂未决定
		c.createTime = new Date();
		
		//执行信息保存
		coinRecordRepository.insert(conn, c);
		orderRepository.insert(conn, o);
	}
	
	/**
	 * @描述 单执行订单添加
	 * @param conn
	 * @param userId
	 * @param title
	 * @param price
	 * @param coin
	 * @param pay
	 * @throws Exception
	 */
	public void setOrder(DruidPooledConnection conn,Long userId,String title,Double price,Integer coin,
			Byte pay,String province,String city,String address) throws Exception {
		Order o = new Order();
		o.id = IDUtils.getSimpleId();
		o.userId = userId;
		o.title = title;
		o.price = price;
		o.coin = coin;
		o.pay = pay;
		o.province = province;
		o.city = city;
		o.address = address;
		o.send = Order.SEND_OFF;     //订单创建时默认为未发货状态
		o.creatTime = new Date();
		o.coinrecId = 0L;
		orderRepository.insert(conn, o);
	}
	
	/**
	 * @描述 根据ID查询积分操作事件
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CoinRecord getCoinRecordById(DruidPooledConnection conn,Long id) throws Exception {
		return coinRecordRepository.getCoinRecordById(conn, id);
	}
	
	/**
	 * @描述 根据用户ID查询用户积分操作记录列表
	 * @param conn
	 * @param userId
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<CoinRecord> getCoinRecordByUserId(DruidPooledConnection conn,Long userId,Integer count,Integer offset) throws Exception{
		return coinRecordRepository.getCoinRecordByUserid(conn, userId, count, offset);
	}
	
	/**
	 * @描述 根据订单ID移除订单
	 * @param conn
	 * @param id
	 * @throws Exception
	 */
	public void delOrder(DruidPooledConnection conn,Long id) throws Exception {
		orderRepository.deleteByKey(conn, "id", new Object[] {id});
	}
	
	/**
	 * @描述 订单发货
	 * @param conn
	 * @param id
	 * @throws Exception
	 */
	public void OrderSend(DruidPooledConnection conn,Long id) throws Exception {
		Order order = new Order();
		order.send = Order.SEND_ON;
		orderRepository.updateByKey(conn, "id", new Object[] {id}, order, true);
	}

}
