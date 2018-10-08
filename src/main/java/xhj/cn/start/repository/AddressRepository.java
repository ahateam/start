package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Address;

public class AddressRepository extends RDSRepository<Address> {
	
	private static AddressRepository ins;

	public static synchronized AddressRepository getInstance() {
		if (null == ins) {
			ins = new AddressRepository();
		}
		return ins;
	}
	
	private AddressRepository() {
		super(Address.class);
	}
	
	/**
	 * @描述 根据ID查询地址信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Address getAddressById(DruidPooledConnection conn,Long id) throws Exception{
		return get(conn, "WHERE id = ?", new Object[] {id});
	}
	
	/**
	 * @描述 根据用户ID获取用户保存的地址列表
	 * @param conn
	 * @param userId
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Address> getAddressByUserId(DruidPooledConnection conn,Long userId,Integer count,Integer offset) throws Exception {
		return getList(conn, "WHERE user_id = ?", new Object[] {userId}, count, offset);
	}
	
	/**
	 * @描述 更改已保存地址信息
	 * @param conn
	 * @param id
	 * @param address
	 * @throws ServerException
	 */
	public void updateAddressById(DruidPooledConnection conn,Long id,Address address) throws Exception {
		update(conn, "WHERE id = ?", new Object[] {id}, address, true);
	}

}
