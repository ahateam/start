package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Present;

public class PresentRepository extends RDSRepository<Present> {
	
	private static PresentRepository ins;

	public static synchronized PresentRepository getInstance() {
		if (null == ins) {
			ins = new PresentRepository();
		}
		return ins;
	}
	
	private PresentRepository() {
		super(Present.class);
	}
	
	/**
	 * @描述 根据礼品ID拉取礼品信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Present getPresentById(DruidPooledConnection conn,Long id) throws Exception {
		return get(conn, "where id = ?", new Object[] {id});
	}
	
	
	/**
	 * @描述 根据对应参数拉取礼品信息
	 * @param conn
	 * @param all
	 * @return
	 * @throws Exception
	 */
	public Present getPresentByKeys(DruidPooledConnection conn,List all) throws Exception {
		return getByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
	}
	
	/**
	 * @描述 获取所有礼品列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Present> getAllPresentList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return getList(conn, null, null, count, offset);
	}
	
	/**
	 * @描述 根据对应要求获取礼品列表
	 * @param conn
	 * @param all
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Present> getPresentList(DruidPooledConnection conn,List all,Integer count,Integer offset) throws Exception{
		return getListByKeys(conn, (String[])all.get(0), (Object[])all.get(1), count, offset);
	}
	
	/**
	 * @描述 根据要求修改礼品对应信息
	 * @param conn
	 * @param store
	 * @throws Exception
	 */
	public void updatePresent(DruidPooledConnection conn,List all, Present present) throws Exception {
		updateByKeys(conn, (String[])all.get(0), (Object[])all.get(1), present, true);
	}
	
	/**
	 * @描述 根据礼品ID修改对应礼品信息
	 * @param conn
	 * @param id
	 * @param present
	 * @throws Exception
	 */
	public void updatePresentById(DruidPooledConnection conn,Long id, Present present) throws Exception {
		update(conn, "where id = ?", new Object[] {id}, present, true);
	}
	
	/**
	 * @描述 根据标签查询礼品列表
	 * @param conn
	 * @param tagKey
	 * @param tags
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Present> getPresentListByTags(DruidPooledConnection conn,String tagKey, 
			JSONArray tags,Integer count,Integer offset) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("WHERE ");
		sb.append("JSON_CONTAINS(tags->'$.").append(tagKey).append("', '");
		for (Object tag : tags) {
			sb.append("\"").append(tag).append("\",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("')");
		return getList(conn, sb.toString(), null, count, offset);
	}
	
	/**
	 * @描述 根据礼品标题模糊查询
	 * @param conn
	 * @param title
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Present> getPresentLikeTitle(DruidPooledConnection conn,String title,Integer count,Integer offset) throws Exception{
		return getList(conn, "WHERE title like '%"+title+"%'", null, count, offset);
	}

}
