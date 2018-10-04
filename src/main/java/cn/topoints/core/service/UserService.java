package cn.topoints.core.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;

import cn.topoints.core.domain.User;
import cn.topoints.core.domain.UserSession;
import cn.topoints.core.repository.UserRepository;
import cn.topoints.core.repository.UserSessionRepository;
import cn.topoints.core.repository.UserTagsRepository;
import cn.topoints.utils.CacheCenter;
import cn.topoints.utils.IDUtils;
import cn.topoints.utils.api.BaseRC;
import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.ots.OTSAutoCloseableClient;

public class UserService {

	private static Logger log = LoggerFactory.getLogger(UserService.class);

	private static UserService ins;

	public static synchronized UserService getInstance() {
		if (null == ins) {
			ins = new UserService();
		}
		return ins;
	}

	private UserRepository userRepository;
	private UserSessionRepository userSessionRepository;
	private UserTagsRepository userTagsRepository;

	private UserService() {
		try {
			userRepository = UserRepository.getInstance();
			userSessionRepository = UserSessionRepository.getInstance();
			userTagsRepository = UserTagsRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public UserSession putUserSession(OTSAutoCloseableClient client, Long appId, Long userId, Byte userLevel,
			Date loginTime, String loginToken) throws Exception {

		UserSession ret = new UserSession();
		ret.userId = userId;
		ret.appId = appId;
		ret.level = userLevel;
		ret.loginTime = loginTime;
		ret.loginToken = loginToken;

		// 先放入Session缓存，再放入存储
		CacheCenter.SESSION_CACHE.put(userId, ret);
		userSessionRepository.putObject(client, ret, true);
		return ret;
	}

	public UserSession getUserSession(OTSAutoCloseableClient client, Long appId, Long userId) throws Exception {
		// 先从缓存中获取
		UserSession session = null;
		try {
			session = CacheCenter.SESSION_CACHE.get(userId);
		} catch (Exception e) {
			session = userSessionRepository.getByKey(client, appId, userId);
		}
		return session;
	}

	public void clearUserSessionById(OTSAutoCloseableClient client, Long appId, Long userId) throws Exception {
		CacheCenter.SESSION_CACHE.invalidate(userId);
		userSessionRepository.deleteByKey(client, appId, userId);
	}

	/**
	 * 用户名密码注册
	 * 
	 * @param appId
	 *            应用编号，用户所属应用（必填）
	 * @param name
	 *            用户名（必填）
	 * @param pwd
	 *            密码（必填）
	 * 
	 * @return 刚注册的用户对象
	 */
	public User registByNameAndPwd(DruidPooledConnection conn, Long appId, String name, String pwd) throws Exception {
		// 用户不存在
		User newUser = new User();
		newUser.id = IDUtils.getSimpleId();
		newUser.appId = appId;
		newUser.createDate = new Date();
		newUser.level = User.LEVEL_BASIC;
		newUser.name = name;

		newUser.pwd = pwd;// TODO 目前是明文，需要加料传输和存储

		// 创建用户
		userRepository.insert(conn, newUser);
		newUser.pwd = null;// 抹掉密码
		return newUser;
	}

	public User loginByNameAndPwd(DruidPooledConnection conn, Long appId, String name, String pwd) throws Exception {
		// 判断用户是否存在
		User existUser = userRepository.getByAppIdAndKey(conn, appId, "name", name);
		if (null == existUser) {
			// 用户不存在
			throw new ServerException(BaseRC.USER_NOT_EXIST);
		} else {
			// 用户已存在，匹配密码
			// TODO 目前是明文，需要加料然后匹配
			if (pwd.equals(existUser.pwd)) {
				return existUser;
			} else {
				// 密码错误
				throw new ServerException(BaseRC.USER_PWD_ERROR);
			}
		}
	}

	public User getUserById(DruidPooledConnection conn, Long userId) throws Exception {
		return userRepository.getByKey(conn, "id", userId);
	}

	public User getUserByName(DruidPooledConnection conn, Long appId, String name) throws Exception {
		return userRepository.getByAppIdAndKey(conn, appId, "name", name);
	}

	public User getUserByMobile(DruidPooledConnection conn, Long appId, String mobile) throws Exception {
		return userRepository.getByAppIdAndKey(conn, appId, "mobile", mobile);
	}

	public User getUserByEmail(DruidPooledConnection conn, Long appId, String email) throws Exception {
		return userRepository.getByAppIdAndKey(conn, appId, "email", email);
	}

	public User getUserByQQOpenId(DruidPooledConnection conn, Long appId, String qqOpenId) throws Exception {
		return userRepository.getByAppIdAndKey(conn, appId, "qq_open_id", qqOpenId);
	}

	public User getUserByWxOpenId(DruidPooledConnection conn, Long appId, String wxOpenId) throws Exception {
		return userRepository.getByAppIdAndKey(conn, appId, "wx_open_id", wxOpenId);
	}

	public User getUserByWbOpenId(DruidPooledConnection conn, Long appId, String wbOpenId) throws Exception {
		return userRepository.getByAppIdAndKey(conn, appId, "wb_open_id", wbOpenId);
	}

	public void setUserNickname(DruidPooledConnection conn, Long userId, String nickname) throws Exception {
		User forUpdate = new User();
		forUpdate.nickname = nickname;
		userRepository.updateByKey(conn, "id", userId, forUpdate, true);
	}

	public void setUserSignature(DruidPooledConnection conn, Long userId, String signature) throws Exception {
		User forUpdate = new User();
		forUpdate.signature = signature;
		userRepository.updateByKey(conn, "id", userId, forUpdate, true);
	}

	public int deleteUserById(DruidPooledConnection conn, Long userId) throws Exception {
		return userRepository.deleteByKey(conn, "id", userId);
	}

	public User auth(DruidPooledConnection conn, Long userId) throws Exception {
		// 先判断user是否存在
		User user = userRepository.getByKey(conn, "id", userId);
		if (null == user) {
			// user不存在
			throw new ServerException(BaseRC.USER_NOT_EXIST);
		} else {
			// 再判断user状态是否有效，TODO 目前status没有启用
			if (User.STATUS_NORMAL == user.status) {
				// 正常状态

				// 最后判断令牌授权等是否有效，TODO，目前没有设计

				// 鉴权必定查询，为方便后续使用，将查询到的user返回备用，避免将来查询
				return user;
			} else if (User.STATUS_PENDING == user.status) {
				// 应用正在等待审核
				throw new ServerException(BaseRC.USER_AUTH_PENDING);
			} else if (User.STATUS_STOPED == user.status) {
				// 应用已被停用
				throw new ServerException(BaseRC.USER_AUTH_STOPED);
			} else if (User.STATUS_DELETED == user.status) {
				// 应用已经删除
				throw new ServerException(BaseRC.USER_AUTH_DELETED);
			} else {
				// 未知的用户状态
				throw new ServerException(BaseRC.USER_AUTH_UNKNOWN_STATUS);
			}

		}
	}

	public JSONArray getUserTags(DruidPooledConnection conn, Long userId, String tagKey) throws Exception {
		return userTagsRepository.getTags(conn, userId, tagKey);
	}

	public void addUserTags(DruidPooledConnection conn, Long userId, String tagKey, JSONArray tags) throws Exception {
		userTagsRepository.addTags(conn, userId, tagKey, tags);
	}

	public void removeUserTags(DruidPooledConnection conn, Long userId, String tagKey, JSONArray tags)
			throws Exception {
		userTagsRepository.removeTags(conn, userId, tagKey, tags);
	}
}
