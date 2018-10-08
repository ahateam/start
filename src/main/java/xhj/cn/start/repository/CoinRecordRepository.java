package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.CoinRecord;

public class CoinRecordRepository extends RDSRepository<CoinRecord> {
	
	private static CoinRecordRepository ins;

	public static synchronized CoinRecordRepository getInstance() {
		if (null == ins) {
			ins = new CoinRecordRepository();
		}
		return ins;
	}
	
	private CoinRecordRepository() {
		super(CoinRecord.class);
	}
	
	/**
	 * @描述 根据记录ID拉取记录信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws ServerException
	 */
	public CoinRecord getCoinRecordById(DruidPooledConnection conn,Long id) throws Exception {
		return get(conn, "where id = ?", new Object[] {id});
	}
	
	/**
	 * @描述 根据用户ID拉取用户对应记录信息列表
	 * @param conn
	 * @param id
	 * @return
	 * @throws ServerException
	 */
	public List<CoinRecord> getCoinRecordByUserid(DruidPooledConnection conn,Long userId,Integer count,Integer offset) throws Exception {
		return getList(conn, "where user_id = ?", new Object[] {userId}, count, offset);
	}

}
