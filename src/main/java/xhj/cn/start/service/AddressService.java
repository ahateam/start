package xhj.cn.start.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.IDUtils;
import cn.topoints.utils.api.ServerException;
import xhj.cn.start.domain.Address;
import xhj.cn.start.repository.AddressRepository;

public class AddressService {
	
	private static Logger log = LoggerFactory.getLogger(AddressService.class);

	private static AddressService ins;

	public static synchronized AddressService getInstance() {
		if (null == ins) {
			ins = new AddressService();
		}
		return ins;
	}
	
	private AddressRepository addressRepository;
	
	private AddressService() {
		try {
			addressRepository = AddressRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @描述 根据ID查询地址信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Address getAddressById(DruidPooledConnection conn,Long id) throws Exception{
		return addressRepository.getAddressById(conn, id);
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
		return addressRepository.getAddressByUserId(conn, userId, count, offset);
	}
	
	/**
	 * @描述 更改已保存地址信息
	 * @param conn
	 * @param id
	 * @param address
	 * @throws ServerException
	 */
	public void updateAddressById(DruidPooledConnection conn,Long id,Address address) throws Exception {
		addressRepository.updateAddressById(conn, id, address);
	}
	
	/**
	 * @描述 新增地址
	 * @param conn
	 * @param userId
	 * @param province
	 * @param city
	 * @param address
	 * @throws Exception
	 */
	public void setAddress(DruidPooledConnection conn,Long userId,String province,String city,String address) throws Exception {
		Address a = new Address();
		a.id = IDUtils.getSimpleId();
		a.userId = userId;
		a.province = province;
		a.city = city;
		a.address = address;
		addressRepository.insert(conn, a);
	}
	
	/**
	 * @描述 根据ID删除地址信息
	 * @param conn
	 * @param id
	 * @throws Exception
	 */
	public void delAddressById(DruidPooledConnection conn,Long id) throws Exception {
		addressRepository.deleteByKey(conn, "id", new Object[] {id});
	}

}
