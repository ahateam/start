package cn.topoints.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.core.domain.App;
import cn.topoints.core.repository.AppRepository;
import cn.topoints.utils.IDUtils;
import cn.topoints.utils.api.BaseRC;
import cn.topoints.utils.api.ServerException;

public class AppService {

	private static Logger log = LoggerFactory.getLogger(AppService.class);

	private static AppService ins;

	public static synchronized AppService getInstance() {
		if (null == ins) {
			ins = new AppService();
		}
		return ins;
	}

	private AppRepository appRepository;

	private AppService() {
		try {

			appRepository = AppRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 用户创建应用，目前没有跟用户关联，因此UserId随便填</br>
	 * 也没有做审批状态，将来再做
	 */
	public App createApp(DruidPooledConnection conn, Long userId, String name) throws Exception {
		App app = new App();
		app.id = IDUtils.getSimpleId();
		app.name = name;
		app.userId = userId;
		app.status = App.STATUS_NORMAL;

		app.secret = Long.toHexString(IDUtils.getSimpleId());

		appRepository.insert(conn, app);

		return app;
	}

	public List<App> getAppList(DruidPooledConnection conn, Long userId, Integer count, Integer offset)
			throws Exception {
		return appRepository.getListByKey(conn, "user_id", userId, count, offset);
	}

	public void deleteAppById(DruidPooledConnection conn, Long appId) throws Exception {
		appRepository.deleteByKey(conn, "id", appId);
	}

	public App auth(DruidPooledConnection conn, Long appId) throws Exception {
		// 先判断app是否存在
		App app = appRepository.getByKey(conn, "id", appId);
		if (null == app) {
			// app不存在
			throw new ServerException(BaseRC.APP_NOT_EXIST);
		} else {
			// 再判断app状态是否有效，TODO 目前status没有启用
			if (App.STATUS_NORMAL == app.status) {
				// 正常状态

				// 最后判断令牌授权等是否有效，TODO，目前没有设计

				// 鉴权必定查询，为方便后续使用，将查询到得app返回备用，避免将来查询
				return app;
			} else if (App.STATUS_PENDING == app.status) {
				// 应用正在等待审核
				throw new ServerException(BaseRC.APP_AUTH_PENDING);
			} else if (App.STATUS_STOPED == app.status) {
				// 应用已被停用
				throw new ServerException(BaseRC.APP_AUTH_STOPED);
			} else if (App.STATUS_DELETED == app.status) {
				// 应用已经删除
				throw new ServerException(BaseRC.APP_AUTH_DELETED);
			} else {
				// 未知的应用状态
				throw new ServerException(BaseRC.APP_AUTH_UNKNOWN_STATUS);
			}
		}
	}
}
