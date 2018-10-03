package xhj.cn.start.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;

import cn.topoints.utils.api.Param;
import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.api.http.APIRequest;
import cn.topoints.utils.api.http.APIResponse;
import cn.topoints.utils.api.http.Controller;
import cn.topoints.utils.data.DataSource;
import cn.topoints.utils.data.DataSourceUtils;
import xhj.cn.start.domain.Assistant;
import xhj.cn.start.domain.Store;
import xhj.cn.start.service.ChannelOpeService;

public class ChannelOpeController extends Controller {
	
	private static Logger log = LoggerFactory.getLogger(ChannelOpeController.class);

	private static ChannelOpeController ins;

	public static synchronized ChannelOpeController getInstance(String node) {
		if (null == ins) {
			ins = new ChannelOpeController(node);
		}
		return ins;
	}
	
	private ChannelOpeService channelOpeService;
	private DataSource dsRds;
	
	private ChannelOpeController(String node) {
		super(node);
		try {
			channelOpeService = ChannelOpeService.getInstance();
			dsRds = DataSourceUtils.getDataSource("rdsDefault");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @描述 根据ID获取对应营业员信息
	 * 
	 * @param assistantId
	 *                 营业员ID
	 * @return assistant
	 *                 营业员信息对象
	 * @throws Exception
	 */
	@doCall(paths = "getAssistantById")
	public APIResponse getAssistantById(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long assistantId = Param.getLong(c, "assistantId");
		log.info("This is controller!!");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			Assistant assistant = channelOpeService.getAssistantById(conn, assistantId);
			return APIResponse.getNewSuccessResp(Param.checkNull(assistant));
		}
	}
	
	/**
	 * @描述 获取对应ID门店详情
	 * 
	 * @param storeId
	 *              门店ID
	 * @param storePoiId
	 *              门店微信poi_id
	 * @return
	 * 
	 * @throws Exception
	 */
	public APIResponse getStoreByKeys(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long storeId = Param.getLongDFLT(c, "storeId", null);
		String storePoiId = Param.getString(c, "storePoiId");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			Store store = channelOpeService.getStoreByKeys(conn, storeId, storePoiId, null, null, null, null);
			return APIResponse.getNewSuccessResp(Param.checkNull(store));
		}
	}
	
	
	public APIResponse getAllAssistantTable(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Integer count = Param.getInteger(c, "count");
		Integer offset = Param.getInteger(c, "offset");
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			List<Assistant> allAssistant = channelOpeService.getAllAssistantTable(conn, count, offset);
			return APIResponse.getNewSuccessResp(allAssistant);
		}
	}

}
