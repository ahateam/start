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
	 * @描述 根据需求参数拉取用户信息
	 * @param conn
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public User getByUserKeys(DruidPooledConnection conn, List all) throws Exception{
		return getByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
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
