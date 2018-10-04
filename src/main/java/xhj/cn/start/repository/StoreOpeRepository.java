package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Store;

public class StoreOpeRepository extends RDSRepository<Store> {
	
	private static StoreOpeRepository ins;

	public static synchronized StoreOpeRepository getInstance() {
		if (null == ins) {
			ins = new StoreOpeRepository();
		}
		return ins;
	}
	
	private StoreOpeRepository() {
		super(Store.class);
	}
	
	/**
	 * @描述 根据对应参数拉取门店信息
	 * @param conn
	 * @param all
	 * @return
	 * @throws Exception
	 */
	public Store getStoreByKeys(DruidPooledConnection conn,List all) throws Exception {
		return getByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
	}
	
	/**
	 * @描述 获取所有门店列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Store> getAllStoreList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return getList(conn, null, null, count, offset);
	}
	
	/**
	 * @描述 根据对应要求获取门店列表
	 * @param conn
	 * @param all
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Store> getStoreList(DruidPooledConnection conn,List all,Integer count,Integer offset) throws Exception{
		return getListByKeys(conn, (String[])all.get(0), (Object[])all.get(1), count, offset);
	}
	
	
	/**
	 * @描述 根据要求修改门店对应信息
	 * @param conn
	 * @param store
	 * @throws Exception
	 */
	public void updateStore(DruidPooledConnection conn,List all, Store store) throws Exception {
		updateByKeys(conn, (String[])all.get(0), (Object[])all.get(1), store, true);
	}

}
