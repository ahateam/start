package cn.topoints.core.service;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.core.domain.App;
import cn.topoints.core.domain.User;

public class ServiceUtils {

	private static AppService appService;
	private static UserService userService;

	static {
		appService = AppService.getInstance();
		userService = UserService.getInstance();
	}

	public static App appAuth(DruidPooledConnection conn, Long appId) throws Exception {
		return new App();// for test
		// return appService.auth(conn, appId);
	}

	public static User userAuth(DruidPooledConnection conn, Long userId) throws Exception {
		User u = new User();
		u.id = userId;
		return u;
		// return userService.auth(conn, userId);
	}

}
