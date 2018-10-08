package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Order;

public class OrderRepository extends RDSRepository<Order> {
	
	private static OrderRepository ins;

	public static synchronized OrderRepository getInstance() {
		if (null == ins) {
			ins = new OrderRepository();
		}
		return ins;
	}
	
	private OrderRepository() {
		super(Order.class);
	}
	
	/**
	 * @描述 根据订单ID拉取订单信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Order getOrderById(DruidPooledConnection conn,Long id) throws Exception {
		return get(conn, "where id = ?", new Object[] {id});
	}
	
	/**
	 * @描述 根据用户ID拉取用户对应订单列表
	 * @param conn
	 * @param id
	 * @return
	 * @throws ServerException
	 */
	public List<Order> getOrderByUserid(DruidPooledConnection conn,Long userId,Integer count,Integer offset) throws Exception {
		return getList(conn, "where user_id = ?", new Object[] {userId}, count, offset);
	}
	
	/**
	 * @描述 获取所有列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Order> getAllOrderList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return getList(conn, null, null, count, offset);
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
		return getList(conn, "where pay = ? and send = ?", new Object[] {pay,send}, count, offset);
	}

}
