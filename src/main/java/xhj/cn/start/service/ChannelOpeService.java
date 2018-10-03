package xhj.cn.start.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.ServerException;
import xhj.cn.start.domain.Assistant;
import xhj.cn.start.domain.Store;
import xhj.cn.start.repository.AssistantOpeRepository;
import xhj.cn.start.repository.StoreOpeRepository;
import xhj.cn.start.util.GetDomain;

public class ChannelOpeService {
	
	private static Logger log = LoggerFactory.getLogger(ChannelOpeService.class);

	private static ChannelOpeService ins;

	public static synchronized ChannelOpeService getInstance() {
		if (null == ins) {
			ins = new ChannelOpeService();
		}
		return ins;
	}
	
	private AssistantOpeRepository assistantOpeRepository;
	private StoreOpeRepository storeOpeRepository;
	private GetDomain gd = new GetDomain();
	
	private ChannelOpeService() {
		try {
			assistantOpeRepository = AssistantOpeRepository.getInstance();
			storeOpeRepository = StoreOpeRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @描述 根据对应要求查询营业员
	 * @param conn
	 * @param assistantId
	 * @param assistantName
	 * @param assistantStorePoiId
	 * @return
	 * @throws Exception
	 */
	public Assistant getAssistantByKeys(DruidPooledConnection conn, Long assistantId, String assistantName, 
			String assistantStorePoiId) throws Exception {
		Assistant a = new Assistant();
		a.assistantId = assistantId;
		a.assistantName = assistantName;
		a.assistantStorePoiId = assistantStorePoiId;
		List<Object> domain = gd.getDomain(a.getClass());
		return assistantOpeRepository.getAssistantByKeys(conn, domain);
	}
	
	
	public Assistant getAssistantById(DruidPooledConnection conn, Long assistantId) throws Exception {
		return assistantOpeRepository.getAssistantById(conn, assistantId);
	}
	
	/**
	 * @描述 根据对应要求查询门店
	 * @param conn
	 * @param assistantId
	 * @param assistantName
	 * @param assistantStorePoiId
	 * @return
	 * @throws Exception
	 */
	public Store getStoreByKeys(DruidPooledConnection conn, Long storeId, String storePoiId, 
			String storeBusinessName, String storeProvince, String storeCity, String storeBranchName) throws Exception {
		Store s = new Store();
		s.storeId = storeId;
		s.storePoiId = storePoiId;
		s.storeBusinessName = storeBusinessName;
		s.storeProvince = storeProvince;
		s.storeCity = storeCity;
		s.storeBranchName = storeBranchName;
		List<Object> domain = gd.getDomain(s.getClass());
		return storeOpeRepository.getStoreByKeys(conn, domain);
	}
	
	/**
	 * @描述 获取所有营业员列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Assistant> getAllAssistantTable(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return assistantOpeRepository.getAllAssistantTable(conn, count, offset);
	}
	
	/**
	 * @描述 获取所有门店列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Store> getAllStoreTable(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return storeOpeRepository.getAllStoreTable(conn, count, offset);
	}
	
	/**
	 * @描述 根据对应信息获取营业员列表
	 * @param conn
	 * @param assistantId
	 * @param assistantName
	 * @param assistantStorePoiId
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Assistant> getAssistantTable(DruidPooledConnection conn, Long assistantId, String assistantName, 
			String assistantStorePoiId,Integer count,Integer offset) throws Exception{
		Assistant a = new Assistant();
		a.assistantId = assistantId;
		a.assistantName = assistantName;
		a.assistantStorePoiId = assistantStorePoiId;
		List<Object> domain = gd.getDomain(a.getClass());
		return assistantOpeRepository.getAssistantTable(conn, domain, count, offset);
	}
	
	/**
	 * @描述 根据对应信息获取门店列表
	 * @param conn
	 * @param assistantId
	 * @param assistantName
	 * @param assistantStorePoiId
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Store> getStoreTable(DruidPooledConnection conn, Long storeId, String storePoiId, 
			String storeBusinessName, String storeProvince, String storeCity, String storeBranchName,
			Integer count,Integer offset) throws Exception{
		Store s = new Store();
		s.storeId = storeId;
		s.storePoiId = storePoiId;
		s.storeBusinessName = storeBusinessName;
		s.storeProvince = storeProvince;
		s.storeCity = storeCity;
		s.storeBranchName = storeBranchName;
		List<Object> domain = gd.getDomain(s.getClass());
		return storeOpeRepository.getStoreTable(conn, domain, count, offset);
	}
	
	/**
	 * @描述 创建营业员
	 * @param conn
	 * @param assistantId
	 * @param assistantName
	 * @param assistantStorePoiId
	 * @return
	 * @throws Exception
	 */
	public Assistant setAssistant(DruidPooledConnection conn, Long assistantId, String assistantName, 
			String assistantStorePoiId) throws Exception {
		Assistant a = new Assistant();
		a.assistantId = assistantId;
		a.assistantName = assistantName;
		a.assistantStorePoiId = assistantStorePoiId;
		assistantOpeRepository.setAssistant(conn, a);
		return a;
	}
	
	/**
	 * @描述 创建门店
	 * @param conn
	 * @param storeId
	 * @param storePoiId
	 * @param storeBusinessName
	 * @param storeProvince
	 * @param storeCity
	 * @param storeBranchName
	 * @return
	 * @throws Exception
	 */
	public Store setStore(DruidPooledConnection conn, Long storeId, String storePoiId, 
			String storeBusinessName, String storeProvince, String storeCity, String storeBranchName) throws Exception {
		Store s = new Store();
		s.storeId = storeId;
		s.storePoiId = storePoiId;
		s.storeBusinessName = storeBusinessName;
		s.storeProvince = storeProvince;
		s.storeCity = storeCity;
		s.storeBranchName = storeBranchName;
		storeOpeRepository.setStore(conn, s);
		return s;
	}
	
	/**
	 * @描述 根据需求清除营业员
	 * @param conn
	 * @param assistantId
	 * @param assistantName
	 * @param assistantStorePoiId
	 * @throws Exception
	 */
	public void delAssistant(DruidPooledConnection conn, Long assistantId, String assistantName, 
			String assistantStorePoiId) throws Exception {
		Assistant a = new Assistant();
		a.assistantId = assistantId;
		a.assistantName = assistantName;
		a.assistantStorePoiId = assistantStorePoiId;
		List<Object> domain = gd.getDomain(a.getClass());
		assistantOpeRepository.delAssistant(conn, domain);
	}
	
	/**
	 * @描述 根据需求清除门店
	 * @param conn
	 * @param storeId
	 * @param storePoiId
	 * @param storeBusinessName
	 * @param storeProvince
	 * @param storeCity
	 * @param storeBranchName
	 * @throws Exception
	 */
	public void delStore(DruidPooledConnection conn, Long storeId, String storePoiId, 
			String storeBusinessName, String storeProvince, String storeCity, String storeBranchName) throws Exception {
		Store s = new Store();
		s.storeId = storeId;
		s.storePoiId = storePoiId;
		s.storeBusinessName = storeBusinessName;
		s.storeProvince = storeProvince;
		s.storeCity = storeCity;
		s.storeBranchName = storeBranchName;
		List<Object> domain = gd.getDomain(s.getClass());
		storeOpeRepository.delStore(conn, domain);
	}
	
	/**
	 * @描述 修改对应营业员信息
	 * @param conn
	 * @param assistantId
	 * @param assistantName
	 * @param assistantStorePoiId
	 * @param assistant
	 * @throws Exception
	 */
	public void updateAssistant(DruidPooledConnection conn, Long assistantId, String assistantName, 
			String assistantStorePoiId,Assistant assistant) throws Exception {
		Assistant a = new Assistant();
		a.assistantId = assistantId;
		a.assistantName = assistantName;
		a.assistantStorePoiId = assistantStorePoiId;
		List<Object> domain = gd.getDomain(a.getClass());
		assistantOpeRepository.updateAssistant(conn, domain, assistant);
	}
	
	/**
	 * @描述 修改对应门店信息
	 * @param conn
	 * @param storeId
	 * @param storePoiId
	 * @param storeBusinessName
	 * @param storeProvince
	 * @param storeCity
	 * @param storeBranchName
	 * @param store
	 * @throws Exception
	 */
	public void updateStore(DruidPooledConnection conn, Long storeId, String storePoiId, 
			String storeBusinessName, String storeProvince, String storeCity, String storeBranchName,Store store) throws Exception {
		Store s = new Store();
		s.storeId = storeId;
		s.storePoiId = storePoiId;
		s.storeBusinessName = storeBusinessName;
		s.storeProvince = storeProvince;
		s.storeCity = storeCity;
		s.storeBranchName = storeBranchName;
		List<Object> domain = gd.getDomain(s.getClass());
		storeOpeRepository.updateStore(conn, domain, store);
	}

}
