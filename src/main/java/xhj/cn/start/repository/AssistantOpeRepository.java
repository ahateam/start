package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Assistant;

public class AssistantOpeRepository extends RDSRepository<Assistant> {
	
	private static AssistantOpeRepository ins;

	public static synchronized AssistantOpeRepository getInstance() {
		if (null == ins) {
			ins = new AssistantOpeRepository();
		}
		return ins;
	}
	
	private AssistantOpeRepository() {
		super(Assistant.class);
	}
	
	
	/**
	 * @描述 根据对应参数拉取营业员信息
	 * @param conn
	 * @param all
	 * @return
	 * @throws Exception
	 */
	public Assistant getAssistantByKeys(DruidPooledConnection conn,List all) throws Exception {
		return getByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
	}
	
	/**
	 * @描述 获取所有营业员列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Assistant> getAllAssistantTable(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return getList(conn, null, null, count, offset);
	}
	
	/**
	 * @描述 根据对应要求获取营业员列表
	 * @param conn
	 * @param all
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Assistant> getAssistantTable(DruidPooledConnection conn,List all,Integer count,Integer offset) throws Exception{
		return getListByKeys(conn, (String[])all.get(0), (Object[])all.get(1), count, offset);
	}
	
	/**
	 * @描述 添加营业员信息
	 * @param conn
	 * @param Assistant
	 * @throws Exception
	 */
	public void setAssistant(DruidPooledConnection conn,Assistant assistant) throws Exception {
		insert(conn, assistant);
	}
	
	/**
	 * @描述 根据要求清除营业员
	 * @param conn
	 * @param Assistant
	 * @throws Exception
	 */
	public void delAssistant(DruidPooledConnection conn,List all) throws Exception {
		deleteByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
	}
	
	/**
	 * @描述 根据要求修改营业员对应信息
	 * @param conn
	 * @param Assistant
	 * @throws Exception
	 */
	public void updateAssistant(DruidPooledConnection conn,List all, Assistant assistant) throws Exception {
		updateByKeys(conn, (String[])all.get(0), (Object[])all.get(1), assistant, true);
	}

}
