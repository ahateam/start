package xhj.cn.start.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;

import xhj.cn.start.domain.User;
import xhj.cn.start.repository.UserOpeRepoistory;
import xhj.cn.start.util.GetDomain;

public class UserOpeService {
	
	private static Logger log = LoggerFactory.getLogger(UserOpeService.class);

	private static UserOpeService ins;

	public static synchronized UserOpeService getInstance() {
		if (null == ins) {
			ins = new UserOpeService();
		}
		return ins;
	}
	
	private UserOpeRepoistory userOpeReporistory;
	
	private UserOpeService() {
		try {
			userOpeReporistory = UserOpeRepoistory.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	GetDomain getDomain = new GetDomain();
	
	/**
	 * @描述 用户列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<User> userTable(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return userOpeReporistory.getUserTable(conn, count, offset);
	}
	
	/**
	 * @描述 根据用户openID拉取用户基本信息
	 * @param conn
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public User getByUserOpenId(DruidPooledConnection conn, String openId) throws Exception {
		User user = new User();
		user.user_openId = openId;
		List<Object> list = getDomain.getDomain(user.getClass());
		return userOpeReporistory.getByUserKeys(conn, list);
	}

}
