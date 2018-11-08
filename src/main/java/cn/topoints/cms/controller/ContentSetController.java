package cn.topoints.cms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.topoints.cms.domain.Content;
import cn.topoints.cms.domain.ContentSet;
import cn.topoints.cms.service.ContentSetService;
import cn.topoints.core.domain.App;
import cn.topoints.core.domain.User;
import cn.topoints.core.service.ServiceUtils;
import cn.topoints.utils.api.Param;
import cn.topoints.utils.api.http.APIRequest;
import cn.topoints.utils.api.http.APIResponse;
import cn.topoints.utils.api.http.Controller;
import cn.topoints.utils.data.DataSource;
import cn.topoints.utils.data.DataSourceUtils;

public class ContentSetController extends Controller {

	private static Logger log = LoggerFactory.getLogger(ContentSetController.class);

	private static ContentSetController ins;

	public static synchronized ContentSetController getInstance(String node) {
		if (null == ins) {
			ins = new ContentSetController(node);
		}
		return ins;
	}

	private DataSource dsRds;
	private ContentSetService contentSetService;

	private ContentSetController(String node) {
		super(node);
		try {
			dsRds = DataSourceUtils.getDataSource("rdsDefault");
			contentSetService = ContentSetService.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 创建内容集合</br>
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号
	 * 
	 * @param title
	 *            集合标题
	 * @param setKey
	 *            集合关键字
	 * @param view
	 *            展示类信息数据（JSON格式，没有可不填）
	 */
	@POSTAPI(path = "createContentSet")
	public APIResponse createContentSet(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		String title = Param.getString(c, "title");
		String setKey = Param.getString(c, "setKey");
		String view = Param.getStringDFLT(c, "view", "");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			ContentSet contentSet = contentSetService.createContentSet(conn, setKey, appId, title, view);
			return APIResponse.getNewSuccessResp(Param.checkNull(contentSet));
		}
	}

	/**
	 * 根据appId，获取app对应的全部ContentSet
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号
	 * @return ContentSet列表
	 */
	@POSTAPI(path = "getContentSetsByAppId")
	public APIResponse getContentSetsByAppId(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			List<ContentSet> contentSet = contentSetService.getContentSetsByAppId(conn, appId, 512, 0);
			return APIResponse.getNewSuccessResp(contentSet);
		}
	}

	/**
	 * 向集合中批量添加多个Content</br>
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号
	 * @param setKey
	 *            集合关键字
	 * @param contents
	 *            内容编号和权重列表(JSONArray格式)，例如：</br>
	 *            [{contentId:123,weight:100},{contentId:234,weight:99},{contentId:345,weight:88}]
	 * @return 结果列表（JSONArray格式）,例如：</br>
	 *         [{setKey:123,appId:100,contentId:123,result:false,msg:"出大事了"},{setKey:123,appId:100,contentId:123,result:true}]
	 */
	@POSTAPI(path = "putContentsInSet")
	public APIResponse putContentsInSet(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		String setKey = Param.getString(c, "setKey");
		JSONArray contents = Param.getArray(c, "contents");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			JSONArray ret = contentSetService.putContentsInSet(conn, setKey, appId, contents);
			return APIResponse.getNewSuccessResp(ret);
		}
	}

	/**
	 * 从集合中批量移除多个Content</br>
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号
	 * @param setKey
	 *            集合关键字
	 * @param contentIds
	 *            内容编号列表(JSONArray格式)
	 * @return 结果列表(JSONArray格式)，例如：</br>
	 *         [{setKey:123,appId:100,contentId:123,result:false,msg:"出大事了"},{setKey:123,appId:100,contentId:123,result:true}]
	 */
	@POSTAPI(path = "removeContentsInSet")
	public APIResponse removeContentsInSet(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		String setKey = Param.getString(c, "setKey");
		JSONArray contentIds = Param.getArray(c, "contentIds");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			JSONArray ret = contentSetService.removeContentsInSet(conn, setKey, appId, contentIds);
			return APIResponse.getNewSuccessResp(ret);
		}
	}

	/**
	 * 批量设置内容权重</br>
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号
	 * @param setKey
	 *            集合关键字
	 * @param contents
	 *            内容编号和权重列表(JSONArray格式)，例如：</br>
	 *            [{contentId:123,weight:100},{contentId:234,weight:99},{contentId:345,weight:88}]
	 * @return 结果列表（JSONArray格式）,例如：</br>
	 *         [{setKey:123,appId:100,contentId:123,result:false,msg:"出大事了"},{setKey:123,appId:100,contentId:123,result:true}]
	 */
	@POSTAPI(path = "batchUpdateContentSetWeight")
	public APIResponse batchUpdateContentSetWeight(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		String setKey = Param.getString(c, "setKey");
		JSONArray contents = Param.getArray(c, "contents");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			JSONArray ret = contentSetService.batchUpdateContentSetWeight(conn, setKey, appId, contents);
			return APIResponse.getNewSuccessResp(ret);
		}
	}

	/**
	 * 根据集合的key查询集合元素
	 * 
	 * @param appId
	 *            内容所属应用编号
	 * @param userId
	 *            用户编号
	 * @param setKey
	 *            集合关键字
	 * @param count
	 *            （选填，默认10）</br>
	 *            分页读取的记录数量
	 * @param offset
	 *            （选填，默认0）</br>
	 *            分页读取的起点位置
	 * @return Content列表
	 */
	@POSTAPI(path = "getContentsBySetKey")
	public APIResponse getContentsBySetKey(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		String setKey = Param.getString(c, "setKey");
		Integer count = Param.getIntegerDFLT(c, "count", 10);
		Integer offset = Param.getIntegerDFLT(c, "offset", 0);

		// 根据关系表中的内容编号去查询出相应的内容（最好用一个sql语句，避免两次查询）
		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			List<Content> ret = contentSetService.getContentsBySetKey(conn, setKey, appId, count, offset);
			return APIResponse.getNewSuccessResp(ret);
		}
	}

}
