package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.User;

public class UserOpeRepoistory extends RDSRepository<User> {

	private static UserOpeRepoistory ins;

	public static synchronized UserOpeRepoistory getInstance() {
		if (null == ins) {
			ins = new UserOpeRepoistory();
		}
		return ins;
	}

	private UserOpeRepoistory() {
		super(User.class);
	}
	
	/**
	 * @描述 根据用户openId拉取用户信息
	 * @param conn
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public User getByUserOpenId(DruidPooledConnection conn, String user_openId) throws Exception{
		return get(conn, "WHERE user_openId=?", new Object[] { user_openId });
	}
	
	/**
	 * @描述 拉取用户列表（分页）
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserTable(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return getList(conn, null, null, count, offset);
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
