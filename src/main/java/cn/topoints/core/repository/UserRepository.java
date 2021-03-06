package cn.topoints.core.repository;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;

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

	public JSONArray getTags(DruidPooledConnection conn, Long userId, String tagKey) throws ServerException {
		return this.getTags(conn, "tags", userId, tagKey);
	}

	public void addTags(DruidPooledConnection conn, Long userId, String tagKey, JSONArray tags) throws ServerException {
		this.addTags(conn, "tags", userId, tagKey, tags);
	}

	public void removeTags(DruidPooledConnection conn, Long userId, String tagKey, JSONArray tags)
			throws ServerException {
		this.removeTags(conn, "tags", userId, tagKey, tags);
	}
}
