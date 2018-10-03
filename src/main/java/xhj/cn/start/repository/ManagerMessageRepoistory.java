package xhj.cn.start.repository;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Manager;

public class ManagerMessageRepoistory extends RDSRepository<Manager> {
	
	private static ManagerMessageRepoistory ins;

	public static synchronized ManagerMessageRepoistory getInstance() {
		if (null == ins) {
			ins = new ManagerMessageRepoistory();
		}
		return ins;
	}

	private ManagerMessageRepoistory() {
		super(Manager.class);
	}
	
	
	public Manager getManager(DruidPooledConnection conn, Long manager_id, String manager_num, String manager_password) {
		return null;
	}

}
