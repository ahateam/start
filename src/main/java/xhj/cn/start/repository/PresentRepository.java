package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Present;

public class PresentRepository extends RDSRepository<Present> {
	
	private static PresentRepository ins;

	public static synchronized PresentRepository getInstance() {
		if (null == ins) {
			ins = new PresentRepository();
		}
		return ins;
	}
	
	private PresentRepository() {
		super(Present.class);
	}
	
	
	/**
	 * @描述 根据对应参数拉取礼品信息
	 * @param conn
	 * @param all
	 * @return
	 * @throws Exception
	 */
	public Present getStoreByKeys(DruidPooledConnection conn,List all) throws Exception {
		return getByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
	}
	
	/**
	 * @描述 获取所有礼品列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Present> getAllStoreList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return getList(conn, null, null, count, offset);
	}
	
	/**
	 * @描述 根据对应要求获取礼品列表
	 * @param conn
	 * @param all
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Present> getStoreList(DruidPooledConnection conn,List all,Integer count,Integer offset) throws Exception{
		return getListByKeys(conn, (String[])all.get(0), (Object[])all.get(1), count, offset);
	}
	
	/**
	 * @描述 根据要求修改礼品对应信息
	 * @param conn
	 * @param store
	 * @throws Exception
	 */
	public void updateStore(DruidPooledConnection conn,List all, Present present) throws Exception {
		updateByKeys(conn, (String[])all.get(0), (Object[])all.get(1), present, true);
	}

}
