package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
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
	
	/**
	 * @描述 动态获取管理员信息
	 * @param conn
	 * @param all
	 * @return
	 * @throws Exception
	 */
	public Manager getManager(DruidPooledConnection conn, List all) throws Exception {
		return getByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
	}

}
