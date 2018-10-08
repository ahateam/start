package xhj.cn.start.repository;

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
	public Coin getCoinById(DruidPooledConnection conn,Long useId) throws ServerException {
		return get(conn, "where user_id = ?", new Object[] {useId});
	}
	
	/**
	 * @描述 修改对应用户积分
	 * @param conn
	 * @param useId
	 * @param coin
	 * @throws ServerException
	 */
	public void updateCoinById(DruidPooledConnection conn,Long useId,Coin coin) throws ServerException {
		update(conn, "where usee_id = ?", new Object[] {useId}, coin, true);
	}

}
