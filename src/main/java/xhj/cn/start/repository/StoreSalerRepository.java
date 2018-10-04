package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.StoreSaler;

public class StoreSalerRepository extends RDSRepository<StoreSaler> {
	
	private static StoreSalerRepository ins;

	public static synchronized StoreSalerRepository getInstance() {
		if (null == ins) {
			ins = new StoreSalerRepository();
		}
		return ins;
	}
	
	private StoreSalerRepository() {
		super(StoreSaler.class);
	}
	
	
	/**
	 * @描述 根据对应参数拉取营业员信息
	 * @param conn
	 * @param all
	 * @return
	 * @throws Exception
	 */
	public StoreSaler getAssistantByKeys(DruidPooledConnection conn,List all) throws Exception {
		return getByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
	}
	/**
	 * @描述 根据ID查询对应营业员
	 * @param conn
	 * @param assistantId
	 * @return
	 * @throws ServerException
	 */
	public StoreSaler getAssistantById(DruidPooledConnection conn,Long assistantId) throws ServerException {
		return get(conn, "where assistant_id = ?", new Object[] {assistantId});
	}
	
	/**
	 * @描述 获取所有营业员列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<StoreSaler> getAllAssistantList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return getList(conn, null, null, count, offset);
	}
	
	/**
	 * @描述 根据对应要求获取营业员列表
	 * @param conn
	 * @param all
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<StoreSaler> getAssistantList(DruidPooledConnection conn,List all,Integer count,Integer offset) throws Exception{
		return getListByKeys(conn, (String[])all.get(0), (Object[])all.get(1), count, offset);
	}
	
	/**
	 * @描述 根据要求修改营业员对应信息
	 * @param conn
	 * @param StoreSaler
	 * @throws Exception
	 */
	public void updateAssistant(DruidPooledConnection conn,List all, StoreSaler assistant) throws Exception {
		updateByKeys(conn, (String[])all.get(0), (Object[])all.get(1), assistant, true);
	}

}
