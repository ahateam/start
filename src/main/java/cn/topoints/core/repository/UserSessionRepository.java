package cn.topoints.core.repository;

import cn.topoints.core.domain.UserSession;
import cn.topoints.utils.data.rds.RDSRepository;

public class UserSessionRepository extends RDSRepository<UserSession> {

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