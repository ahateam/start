package cn.topoints.core.repository;

import cn.topoints.core.domain.UserSession;
import cn.topoints.utils.data.ots.OTSRepository;

public class UserSessionRepository extends OTSRepository<UserSession> {

	private static UserSessionRepository ins;

	public static synchronized UserSessionRepository getInstance() {
		if (null == ins) {
			ins = new UserSessionRepository();
		}
		return ins;
	}

	private UserSessionRepository() {
		super(UserSession.class);
	}

}