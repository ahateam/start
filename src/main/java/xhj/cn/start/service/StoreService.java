package xhj.cn.start.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;

import xhj.cn.start.domain.Store;
import xhj.cn.start.domain.StoreSaler;
import xhj.cn.start.repository.StoreRepository;
import xhj.cn.start.repository.StoreSalerRepository;
import xhj.cn.start.util.GetDomain;

public class StoreService {

	private static Logger log = LoggerFactory.getLogger(StoreService.class);

	private static StoreService ins;

	public static synchronized StoreService getInstance() {
		if (null == ins) {
			ins = new StoreService();
		}
		return ins;
	}

	private StoreSalerRepository assistantOpeRepository;
	private StoreRepository storeOpeRepository;
	private GetDomain gd = new GetDomain();

	private StoreService() {
		try {
			assistantOpeRepository = StoreSalerRepository.getInstance();
			storeOpeRepository = StoreRepository.getInstance();
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
	public StoreSaler getStoreSalerByKeys(DruidPooledConnection conn, Long assistantId, String assistantName,
			String assistantStorePoiId) throws Exception {
		StoreSaler a = new StoreSaler();
		a.id = assistantId;
		a.name = assistantName;
		a.poiId = assistantStorePoiId;
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
	public StoreSaler getAssistantById(DruidPooledConnection conn, Long assistantId) throws Exception {
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
	public Store getStoreByKeys(DruidPooledConnection conn, Long storeId, String storePoiId, String storeBusinessName,
			String storeProvince, String storeCity) throws Exception {
		Store s = new Store();
		s.id = storeId;
		s.poiId = storePoiId;
		s.name = storeBusinessName;
		s.province = storeProvince;
		s.city = storeCity;
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
	public List<StoreSaler> getAllAssistantList(DruidPooledConnection conn, Integer count, Integer offset)
			throws Exception {
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
	public List<Store> getAllStoreList(DruidPooledConnection conn, Integer count, Integer offset) throws Exception {
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
	public List<StoreSaler> getAssistantList(DruidPooledConnection conn, Long assistantId, String assistantName,
			String assistantStorePoiId, Integer count, Integer offset) throws Exception {
		StoreSaler a = new StoreSaler();
		a.id = assistantId;
		a.name = assistantName;
		a.poiId = assistantStorePoiId;
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
			String storeBusinessName, String storeProvince, String storeCity, Integer count,
			Integer offset) throws Exception {
		Store s = new Store();
		s.id = storeId;
		s.poiId = storePoiId;
		s.name = storeBusinessName;
		s.province = storeProvince;
		s.city = storeCity;
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
	public StoreSaler setAssistant(DruidPooledConnection conn, Long assistantId, String assistantName,
			String assistantStorePoiId) throws Exception {
		StoreSaler a = new StoreSaler();
		a.id = assistantId;
		a.name = assistantName;
		a.poiId = assistantStorePoiId;
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
	public Store setStore(DruidPooledConnection conn, Long storeId, String storePoiId, String storeBusinessName,
			String storeProvince, String storeCity) throws Exception {
		Store s = new Store();
		s.id = storeId;
		s.poiId = storePoiId;
		s.name = storeBusinessName;
		s.province = storeProvince;
		s.city = storeCity;
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
		assistantOpeRepository.deleteByKey(conn, "assistant_id", new Object[] { assistantId });
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
		storeOpeRepository.deleteByKey(conn, "store_id", new Object[] { storeId });
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
			String assistantStorePoiId, StoreSaler assistant) throws Exception {
		StoreSaler a = new StoreSaler();
		a.id = assistantId;
		a.name = assistantName;
		a.poiId = assistantStorePoiId;
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
	public void updateStore(DruidPooledConnection conn, Long storeId, String storePoiId, String storeBusinessName,
			String storeProvince, String storeCity, Store store) throws Exception {
		Store s = new Store();
		s.id = storeId;
		s.poiId = storePoiId;
		s.name = storeBusinessName;
		s.province = storeProvince;
		s.city = storeCity;
		List<Object> domain = gd.getDomain(s);
		storeOpeRepository.updateStore(conn, domain, store);
	}

}
