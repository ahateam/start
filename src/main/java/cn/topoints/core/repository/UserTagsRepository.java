package cn.topoints.core.repository;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;

import cn.topoints.core.domain.UserTags;
import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;

/**
 * 
 */
public class UserTagsRepository extends RDSRepository<UserTags> {

	private static UserTagsRepository ins;

	public static synchronized UserTagsRepository getInstance() {
		if (null == ins) {
			ins = new UserTagsRepository();
		}
		return ins;
	}

	private UserTagsRepository() {
		super(UserTags.class);
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
