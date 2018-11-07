package xhj.cn.start.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;

import cn.topoints.utils.IDUtils;
import cn.topoints.utils.api.Param;
import cn.topoints.utils.api.http.APIRequest;
import cn.topoints.utils.api.http.APIResponse;
import cn.topoints.utils.api.http.Controller;
import cn.topoints.utils.data.DataSource;
import cn.topoints.utils.data.DataSourceUtils;
import xhj.cn.start.domain.Store;
import xhj.cn.start.domain.StoreSaler;
import xhj.cn.start.service.StoreService;

public class StoreController extends Controller {

	private static Logger log = LoggerFactory.getLogger(StoreController.class);

	private static StoreController ins;

	public static synchronized StoreController getInstance(String node) {
		if (null == ins) {
			ins = new StoreController(node);
		}
		return ins;
	}

	private StoreService channelOpeService;
	private DataSource dsRds;

	private StoreController(String node) {
		super(node);
		try {
			channelOpeService = StoreService.getInstance();
			dsRds = DataSourceUtils.getDataSource("rdsDefault");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @描述 根据ID获取对应营业员信息
	 * 
	 * @param assistantId
	 *            营业员ID
	 * @return assistant 营业员信息对象
	 * @throws Exception
	 */
	@POSTAPI(path = "getAssistantById")
	public APIResponse getAssistantById(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long assistantId = Param.getLong(c, "assistantId");
		log.info("This is controller!!");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			StoreSaler assistant = channelOpeService.getStoreSalerByKeys(conn, assistantId, null, null);
			return APIResponse.getNewSuccessResp(Param.checkNull(assistant));
		}
	}

	/**
	 * @描述 获取对应ID门店详情
	 * 
	 * @param storeId
	 *            门店ID
	 * @param storePoiId
	 *            门店微信poi_id
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "getStoreByKeys")
	public APIResponse getStoreByKeys(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long storeId = Param.getLongDFLT(c, "storeId", null);
		String storePoiId = Param.getString(c, "storePoiId");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			Store store = channelOpeService.getStoreByKeys(conn, storeId, storePoiId, null, null, null);
			return APIResponse.getNewSuccessResp(Param.checkNull(store));
		}
	}

	/**
	 * @描述 获取所有营业员列表
	 * 
	 * @param count
	 *            查询数据条数
	 * @param offset
	 *            起始查询数据
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "getAllAssistantList")
	public APIResponse getAllAssistantList(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Integer count = Param.getInteger(c, "count");
		Integer offset = Param.getInteger(c, "offset");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			List<StoreSaler> allAssistant = channelOpeService.getAllAssistantList(conn, count, offset);
			return APIResponse.getNewSuccessResp(allAssistant);
		}
	}

	/**
	 * @描述 获取所有门店列表
	 * 
	 * @param count
	 *            查询数据条数
	 * @param offset
	 *            起始查询数据
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "getAllStoreList")
	public APIResponse getAllStoreList(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Integer count = Param.getInteger(c, "count");
		Integer offset = Param.getInteger(c, "offset");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			List<Store> allStore = channelOpeService.getAllStoreList(conn, count, offset);
			return APIResponse.getNewSuccessResp(allStore);
		}
	}

	/**
	 * @描述 根据需求获取营业员列表
	 * 
	 * @param assistantId
	 *            营业员ID
	 * @param assistantName
	 *            营业员姓名
	 * @param assistantStorePoiId
	 *            营业员对应门店poi_id
	 * @param count
	 *            查询数据条数
	 * @param offset
	 *            起始查询数据
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "getAssistantList")
	public APIResponse getAssistantList(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		// 获取营业员对应信息
		Long assistantId = Param.getLongDFLT(c, "assistantId", null);
		String assistantName = Param.getStringDFLT(c, "assistantName", null);
		String assistantStorePoiId = Param.getStringDFLT(c, "assistantStorePoiId", null);
		// 获取分页参数
		Integer count = Param.getInteger(c, "count");
		Integer offset = Param.getInteger(c, "offset");
		// 执行查询
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			List<StoreSaler> assistantList = new ArrayList<StoreSaler>();
			if (assistantId == null || assistantName == null || assistantStorePoiId == null) {
				assistantList = channelOpeService.getAllAssistantList(conn, count, offset);
			} else {
				assistantList = channelOpeService.getAssistantList(conn, assistantId, assistantName,
						assistantStorePoiId, count, offset);
			}
			return APIResponse.getNewSuccessResp(assistantList);
		}
	}

	/**
	 * @描述 根据需求获取门店列表
	 * 
	 * @param storeId
	 *            门店ID
	 * @param storePoiId
	 *            门店poi_id
	 * @param storeBusinessName
	 *            门店名称
	 * @param storeProvince
	 *            门店所在省份
	 * @param storeCity
	 *            门店所在城市
	 * @param count
	 *            查询数据条数
	 * @param offset
	 *            起始查询数据
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "getStoreList")
	public APIResponse getStoreList(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		// 获取门店对应信息
		Long storeId = Param.getLongDFLT(c, "storeId", null);
		String storePoiId = Param.getStringDFLT(c, "storePoiId", null);
		String storeBusinessName = Param.getStringDFLT(c, "storeBusinessName", null);
		String storeProvince = Param.getStringDFLT(c, "storeProvince", null);
		String storeCity = Param.getStringDFLT(c, "storeCity", null);
		// 获取分页参数
		Integer count = Param.getInteger(c, "count");
		Integer offset = Param.getInteger(c, "offset");
		// 执行查询
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			List<Store> storeList = new ArrayList<Store>();
			if (storeId == null || storePoiId == null || storeBusinessName == null || storeProvince == null
					|| storeCity == null) {
				storeList = channelOpeService.getAllStoreList(conn, count, offset);
			} else {
				storeList = channelOpeService.getStoreList(conn, storeId, storePoiId, storeBusinessName, storeProvince,
						storeCity, count, offset);
			}
			return APIResponse.getNewSuccessResp(storeList);
		}
	}

	/**
	 * @描述 创建营业员
	 * 
	 * @param assistantId
	 *            营业员ID
	 * @param assistantName
	 *            营业员姓名
	 * @param assistantStorePoiId
	 *            营业员对应门店poi_id
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "setAssistant")
	public APIResponse setAssistant(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		// 获取营业员对应信息
		Long assistantId = IDUtils.getSimpleId();
		String assistantName = Param.getString(c, "assistantName");
		String assistantStorePoiId = Param.getString(c, "assistantStorePoiId");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			channelOpeService.setAssistant(conn, assistantId, assistantName, assistantStorePoiId);
			return APIResponse.getNewSuccessResp(assistantId);
		}
	}

	/**
	 * @描述 创建门店
	 * 
	 * @param storeId
	 *            门店ID
	 * @param storePoiId
	 *            门店poi_id
	 * @param storeBusinessName
	 *            门店名称
	 * @param storeProvince
	 *            门店所在省份
	 * @param storeCity
	 *            门店所在城市
	 * @param storeBranchName
	 *            分店名
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "setStore")
	public APIResponse setStore(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		// 获取门店对应信息
		Long storeId = IDUtils.getSimpleId();
		String storePoiId = Param.getString(c, "storePoiId");
		String storeBusinessName = Param.getString(c, "storeBusinessName");
		String storeProvince = Param.getString(c, "storeProvince");
		String storeCity = Param.getString(c, "storeCity");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			channelOpeService.setStore(conn, storeId, storePoiId, storeBusinessName, storeProvince, storeCity);
			return APIResponse.getNewSuccessResp(storeId);
		}
	}

	/**
	 * @描述 根据ID清除营业员
	 * 
	 * @param assistantId
	 *            营业员ID
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "delAssistant")
	public APIResponse delAssistant(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		// 获取营业员ID
		Long assistantId = Param.getLong(c, "assistantId");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			channelOpeService.delAssistant(conn, assistantId);
			return APIResponse.getNewSuccessResp();
		}
	}

	/**
	 * @描述 根据ID清除门店
	 * 
	 * @param storeId
	 *            门店ID
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "delStore")
	public APIResponse delStore(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		// 获取门店ID
		Long storeId = Param.getLong(c, "storeId");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			channelOpeService.delStore(conn, storeId);
			return APIResponse.getNewSuccessResp();
		}
	}

	/**
	 * @描述 修改营业员信息
	 * 
	 * @param updateAssistantId
	 *            营业员ID（修改范围）
	 * @param updateAssistantName
	 *            营业员姓名（修改范围）
	 * @param updateAssistantStorePoiId
	 *            营业员对应门店poi_id（修改范围）
	 * @param assistantName
	 *            将修改营业员姓名信息
	 * @param assistantStorePoiId
	 *            将修改营业员对应门店poi_id信息
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "updateAssistant")
	public APIResponse updateAssistant(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		// 获取修改范围
		Long updateAssistantId = Param.getLongDFLT(c, "updateAssistantId", null);
		String updateAssistantName = Param.getStringDFLT(c, "updateAssistantName", null);
		String updateAssistantStorePoiId = Param.getStringDFLT(c, "updateAssistantStorePoiId", null);
		// 获取修改信息
		StoreSaler assistant = new StoreSaler();
		assistant.name = Param.getStringDFLT(c, "assistantName", null);
		assistant.poiId = Param.getStringDFLT(c, "assistantStorePoiId", null);
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			if (updateAssistantId == null && updateAssistantName == null && updateAssistantStorePoiId == null) {
				log.info("修改范围有误！！");
			} else {
				channelOpeService.updateAssistant(conn, updateAssistantId, updateAssistantName,
						updateAssistantStorePoiId, assistant);
			}
			return APIResponse.getNewSuccessResp();
		}
	}

	/**
	 * @描述 修改门店信息
	 * 
	 * @param storeId
	 *            门店ID（修改范围）
	 * @param storePoiId
	 *            门店poi_id（修改范围）
	 * @param storeBusinessName
	 *            门店名称（修改范围）
	 * @param storeProvince
	 *            门店所在省份（修改范围）
	 * @param storeCity
	 *            门店所在城市（修改范围）
	 * @param storeBranchName
	 *            分店名（修改范围）
	 * @param storeBusinessName
	 *            将修改门店名称
	 * @param storeProvince
	 *            将修改门店所在省份
	 * @param storeCity
	 *            将修改门店所在城市
	 * @param storeBranchName
	 *            将修改分店名
	 * @return
	 * 
	 * @throws Exception
	 */
	@POSTAPI(path = "updateStore")
	public APIResponse updateStore(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		// 获取修改范围
		Long updateStoreId = Param.getLongDFLT(c, "updateStoreId", null);
		String updateStorePoiId = Param.getStringDFLT(c, "updateStorePoiId", null);
		String updateStoreBusinessName = Param.getStringDFLT(c, "updateStoreBusinessName", null);
		String updateStoreProvince = Param.getStringDFLT(c, "updateStoreProvince", null);
		String updateStoreCity = Param.getStringDFLT(c, "updateStoreCity", null);
		// 获取修改信息
		Store store = new Store();
		store.name = Param.getString(c, "storeBusinessName");
		store.province = Param.getString(c, "storeProvince");
		store.city = Param.getString(c, "storeCity");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			if (updateStoreId == null && updateStorePoiId == null && updateStoreBusinessName == null
					&& updateStoreProvince == null && updateStoreCity == null) {
				log.info("修改范围有误！！");
			} else {
				channelOpeService.updateStore(conn, updateStoreId, updateStorePoiId, updateStoreBusinessName,
						updateStoreProvince, updateStoreCity, store);
			}
			return APIResponse.getNewSuccessResp();
		}
	}

}
