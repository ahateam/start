package xhj.cn.start.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;

import cn.topoints.utils.IDUtils;
import cn.topoints.utils.api.ServerException;
import xhj.cn.start.domain.Present;
import xhj.cn.start.repository.PresentRepository;
import xhj.cn.start.util.GetDomain;

public class PresentService {
	
	
	private static Logger log = LoggerFactory.getLogger(PresentService.class);

	private static PresentService ins;

	public static synchronized PresentService getInstance() {
		if (null == ins) {
			ins = new PresentService();
		}
		return ins;
	}
	
	private PresentRepository presentRepository;
	private GetDomain gd = new GetDomain();
	
	private PresentService() {
		try {
			presentRepository = PresentRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @描述 根据礼品ID拉取礼品信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Present getPresentById(DruidPooledConnection conn, Long id) throws Exception {
		return presentRepository.getPresentById(conn, id);
	}
	
	/**
	 * @描述 根据礼品状态拉取礼品列表
	 * @param conn
	 * @param title
	 * @param type
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Present> getPresentByStatus(DruidPooledConnection conn,Byte status,Integer count,Integer offset) throws Exception {
		String key = "status";
		Object[] value = { status };
		return presentRepository.getListByKey(conn, key, value,count,offset);
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
	public List<Present> getPresentByTags(DruidPooledConnection conn,String tagKey, 
			JSONArray tags,Integer count,Integer offset) throws Exception{
		return presentRepository.getPresentListByTags(conn, tagKey, tags, count, offset);
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
		return presentRepository.getPresentLikeTitle(conn, title, count, offset);
	}
	
	/**
	 * @描述 获取所有礼品列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Present> getAllPresent(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return presentRepository.getAllPresentList(conn, count, offset);
	}
	
	/**
	 * @描述 添加礼品
	 * @param conn
	 * @param title
	 * @param price
	 * @param type
	 * @param discription
	 * @param amount
	 * @param tags
	 * @throws Exception
	 */
	public void setPresent(DruidPooledConnection conn,String title,String discription,Byte status,
			Date startTime,Date endTime,Integer amount,String tags) throws Exception {
		Present p = new Present();
		p.id = IDUtils.getSimpleId();
		p.title = title;
		p.discription = discription;
		p.status = status;
		p.startTime = startTime;
		p.endTime = endTime;
		p.amount = amount;
		p.remainingAmount = amount;
		p.createTime = new Date();
		p.tags = tags;
		presentRepository.insert(conn, p);
	}
	
	/**
	 * @描述 根据礼品ID删除礼品
	 * @param conn
	 * @param id
	 * @throws ServerException
	 */
	public void delPresentById(DruidPooledConnection conn,Long id) throws ServerException {
		presentRepository.deleteByKey(conn, "id", new Object[] {id});
	}
	
	/**
	 * @描述 根据ID修改礼品信息/根据状态批量修改礼品信息
	 * @param conn
	 * @param id
	 * @param type
	 * @param Present
	 * @throws Exception
	 */
	public void updatePresent(DruidPooledConnection conn,Long id,Byte status,Present present) throws Exception {
		Present p = new Present();
		p.id = id;
		p.status = status;
		List<Object> all = gd.getDomain(p);
		presentRepository.updatePresent(conn, all, present);
	}

}
