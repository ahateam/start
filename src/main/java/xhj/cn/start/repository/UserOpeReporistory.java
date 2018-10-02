package xhj.cn.start.repository;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.User;

public class UserOpeReporistory extends RDSRepository<User> {

	private static UserOpeReporistory ins;

	public static synchronized UserOpeReporistory getInstance() {
		if (null == ins) {
			ins = new UserOpeReporistory();
		}
		return ins;
	}

	private UserOpeReporistory() {
		super(User.class);
	}
	
	/**
	 * @描述 根据用户openId拉取用户信息
	 * @param conn
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public User getByUserOpenId(DruidPooledConnection conn, String openId) throws Exception{
		return get(conn, "WHERE openId=?", new Object[] { openId });
	}
	
	/**
	 * @描述 存入用户信息
	 * @param conn
	 * @param user
	 * @throws Exception
	 */
	public void setUserMessage(DruidPooledConnection conn, User user) throws Exception{
		insert(conn, user);
	}
	
	
}
