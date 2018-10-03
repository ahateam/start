package cn.topoints.core.repository;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.core.domain.User;
import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;

/**
 * 
 */
public class UserRepository extends RDSRepository<User> {

	private static UserRepository ins;

	public static synchronized UserRepository getInstance() {
		if (null == ins) {
			ins = new UserRepository();
		}
		return ins;
	}

	private UserRepository() {
		super(User.class);
	}

	public User getByAppIdAndKey(DruidPooledConnection conn, Long appId, String key, Object value)
			throws ServerException {
		StringBuffer sb = new StringBuffer("WHERE app_id=? AND ");
		sb.append(key).append("=?");
		return this.get(conn, sb.toString(), new Object[] { appId, value });
	}

}
