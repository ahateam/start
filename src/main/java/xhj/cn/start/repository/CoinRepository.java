package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Coin;

public class CoinRepository extends RDSRepository<Coin> {
	
	private static CoinRepository ins;

	public static synchronized CoinRepository getInstance() {
		if (null == ins) {
			ins = new CoinRepository();
		}
		return ins;
	}
	
	private CoinRepository() {
		super(Coin.class);
	}
	
	/**
	 * @描述 根据ID查询对应用户积分
	 * @param conn
	 * @param assistantId
	 * @return
	 * @throws ServerException
	 */
	public Coin getAssistantById(DruidPooledConnection conn,Long useId) throws ServerException {
		return get(conn, "where user_id = ?", new Object[] {useId});
	}
	
	/**
	 * @描述 根据要求修改用户积分
	 * @param conn
	 * @param StoreSaler
	 * @throws Exception
	 */
	public void updateAssistant(DruidPooledConnection conn,List all, Coin coin) throws Exception {
		updateByKeys(conn, (String[])all.get(0), (Object[])all.get(1), coin, true);
	}

}
