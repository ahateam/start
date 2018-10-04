package xhj.cn.start.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;

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
		List<Object> domain = gd.getDomain(a);
		return assistantOpeRepository.getAssistantByKeys(conn, domain);
	}
	
	/**
	 * @描述 根据ID查询对应营业员
	 * @param conn
	 * @param assistantId
	 * @return
	 * @throws Exception
	 */
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
		List<Object> domain = gd.getDomain(s);
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
	public List<Assistant> getAllAssistantList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return assistantOpeRepository.getAllAssistantList(conn, count, offset);
	}
	
	/**
	 * @描述 获取所有门店列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Store> getAllStoreList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return storeOpeRepository.getAllStoreList(conn, count, offset);
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
	public List<Assistant> getAssistantList(DruidPooledConnection conn, Long assistantId, String assistantName, 
			String assistantStorePoiId,Integer count,Integer offset) throws Exception{
		Assistant a = new Assistant();
		a.assistantId = assistantId;
		a.assistantName = assistantName;
		a.assistantStorePoiId = assistantStorePoiId;
		List<Object> domain = gd.getDomain(a);
		return assistantOpeRepository.getAssistantList(conn, domain, count, offset);
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
	public List<Store> getStoreList(DruidPooledConnection conn, Long storeId, String storePoiId, 
			String storeBusinessName, String storeProvince, String storeCity, String storeBranchName,
			Integer count,Integer offset) throws Exception{
		Store s = new Store();
		s.storeId = storeId;
		s.storePoiId = storePoiId;
		s.storeBusinessName = storeBusinessName;
		s.storeProvince = storeProvince;
		s.storeCity = storeCity;
		s.storeBranchName = storeBranchName;
		List<Object> domain = gd.getDomain(s);
		return storeOpeRepository.getStoreList(conn, domain, count, offset);
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
		assistantOpeRepository.insert(conn, a);
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
		storeOpeRepository.insert(conn, s);
		return s;
	}
	
	/**
	 * @描述 根据ID清除营业员
	 * @param conn
	 * @param assistantId
	 * @param assistantName
	 * @param assistantStorePoiId
	 * @throws Exception
	 */
	public void delAssistant(DruidPooledConnection conn, Long assistantId) throws Exception {
		assistantOpeRepository.deleteByKey(conn, "assistant_id", new Object[]{assistantId});
	}
	
	/**
	 * @描述 根据ID清除门店
	 * @param conn
	 * @param storeId
	 * @param storePoiId
	 * @param storeBusinessName
	 * @param storeProvince
	 * @param storeCity
	 * @param storeBranchName
	 * @throws Exception
	 */
	public void delStore(DruidPooledConnection conn, Long storeId) throws Exception {
		storeOpeRepository.deleteByKey(conn, "store_id", new Object[]{storeId});
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
		List<Object> domain = gd.getDomain(a);
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
		List<Object> domain = gd.getDomain(s);
		storeOpeRepository.updateStore(conn, domain, store);
	}

}
