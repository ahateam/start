package cn.topoints.cms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.topoints.cms.domain.Content;
import cn.topoints.cms.domain.ContentSource;
import cn.topoints.cms.service.ContentService;
import cn.topoints.core.domain.App;
import cn.topoints.core.domain.User;
import cn.topoints.core.service.ServiceUtils;
import cn.topoints.utils.api.BaseRC;
import cn.topoints.utils.api.Param;
import cn.topoints.utils.api.http.APIRequest;
import cn.topoints.utils.api.http.APIResponse;
import cn.topoints.utils.api.http.Controller;
import cn.topoints.utils.data.DataSource;
import cn.topoints.utils.data.DataSourceUtils;

public class ContentController extends Controller {

	private static Logger log = LoggerFactory.getLogger(ContentController.class);

	private static ContentController ins;

	public static synchronized ContentController getInstance(String node) {
		if (null == ins) {
			ins = new ContentController(node);
		}
		return ins;
	}

	private DataSource dsRds;
	private ContentService contentService;

	private ContentController(String node) {
		super(node);
		try {
			dsRds = DataSourceUtils.getDataSource("rdsDefault");

			contentService = ContentService.getInstance();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 创建内容
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号
	 * @param type
	 *            类型</br>
	 *            （TYPE_IMAGE = 0;// 图片数组（相册）</br>
	 *            TYPE_AUDIO = 1;// 音频</br>
	 *            TYPE_VIDEO = 2;// 电影</br>
	 *            TYPE_LIVE = 3;// 直播</br>
	 *            TYPE_H5 = 4;// H5文本</br>
	 *            TYPE_VIDEO_CLIP = 5;// 短视频</br>
	 *            TYPE_VIDEO_SET = 6;// 视频集合（剧集））</br>
	 * @param level
	 *            （选填，默认LEVEL_PUBLIC）</br>
	 *            分级（用于权限控制）</br>
	 *            （未实现）
	 * @param upChannelId
	 *            上传所属专栏编号
	 * @param title
	 *            标题
	 * @param origon
	 *            （选填，默认为appId）</br>
	 *            内容来源
	 * @param meta
	 *            内容元信息，用于进一步标识内容的用途
	 * @param data
	 *            数据</br>
	 *            JSON形式存储内容信息结构体，具体结构体视项目而定
	 * @param secretData
	 *            （选填，默认null）</br>
	 *            私密数据，拥有足够权限才能获取该信息</br>
	 *            付费内容的敏感信息，如视频地址，都存放到这里
	 * @param view
	 *            （选填，默认null）</br>
	 *            展示数据，例如缩略图等其它展示相关的信息
	 */
	@POSTAPI(path = "createContent")
	public APIResponse createContent(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		// id
		// appId
		Byte type = Param.getByte(c, "type");
		// status
		Byte level = Param.getByteDFLT(c, "level", Content.LEVEL_PUBLIC);
		// createTime
		// updateTime
		Long upUserId = userId;
		Long upChannelId = Param.getLong(c, "upChannelId");
		String title = Param.getString(c, "title");
		String origin = Param.getStringDFLT(c, "origin", Long.toString(appId));
		String meta = Param.getString(c, "meta");
		String data = Param.getString(c, "data");
		String secretData = Param.getStringDFLT(c, "secretData", null);
		String view = Param.getStringDFLT(c, "view", null);

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			Content cnt = contentService.createContent(conn, //
					appId, type, level, upUserId, upChannelId, title, (byte) 50, origin, meta, data, secretData, view);

			return APIResponse.getNewSuccessResp(cnt.id);
		}
	}

	/**
	 * 为Content添加标签
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号，用于鉴权
	 * @param contentId
	 *            内容编号
	 * @param tagKey
	 *            标签分组名称
	 * @param tags
	 *            标签列表（JSON数组格式）
	 */
	@POSTAPI(path = "addContentTags")
	public APIResponse addContentTags(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		Long contentId = Param.getLong(c, "contentId");
		String tagKey = Param.getString(c, "tagKey");
		JSONArray tags = Param.getArray(c, "tags");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			Content content = contentService.auth(conn, contentId);// content鉴权

			contentService.addContentTags(conn, contentId, tagKey, tags);

			return APIResponse.getNewSuccessResp();
		}
	}

	/**
	 * 为Content移除标签
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号，用于鉴权
	 * @param contentId
	 *            内容编号
	 * @param tagKey
	 *            标签分组名称
	 * @param tags
	 *            标签列表（JSON数组格式）
	 * 
	 */
	@POSTAPI(path = "removeContentTags")
	public APIResponse removeContentTags(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		Long contentId = Param.getLong(c, "contentId");
		String tagKey = Param.getString(c, "tagKey");
		JSONArray tags = Param.getArray(c, "tags");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			Content content = contentService.auth(conn, contentId);// content鉴权

			contentService.removeContentTags(conn, contentId, tagKey, tags);

			return APIResponse.getNewSuccessResp();
		}
	}

	/**
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号，用于鉴权
	 * @param type
	 *            （选填，默认不参与查询）</br>
	 *            内容类型（可空）
	 * @param status
	 *            （选填，默认不参与查询）</br>
	 *            内容状态
	 * @param level
	 *            （选填，默认不参与查询）</br>
	 *            内容等级
	 * @param upUserId
	 *            （选填，默认不参与查询）</br>
	 *            上传者的用户编号
	 * @param upChannelId
	 *            （选填，默认不参与查询）</br>
	 *            上传专栏的专栏编号
	 * @param tagKey
	 *            标签分组名称
	 * @param tags
	 *            标签列表（JSON数组格式）
	 * @param count
	 *            （选填，默认10）</br>
	 *            分页读取的记录数量
	 * @param offset
	 *            （选填，默认0）</br>
	 *            分页读取的起点位置
	 * 
	 * @return 内容对象数组
	 */
	@POSTAPI(path = "queryContents")
	public APIResponse queryContents(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		Byte type = Param.getByteDFLT(c, "type", null);
		Byte status = Param.getByteDFLT(c, "status", null);
		Byte level = Param.getByteDFLT(c, "level", null);
		Long upUserId = Param.getLongDFLT(c, "upUserId", null);
		Long upChannelId = Param.getLongDFLT(c, "upChannelId", null);

		String tagKey = Param.getString(c, "tagKey");
		JSONArray tags = Param.getArray(c, "tags");

		Integer count = Param.getIntegerDFLT(c, "count", 10);
		Integer offset = Param.getIntegerDFLT(c, "offset", 0);

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			List<Content> ret = contentService.queryContents(conn, appId, type, status, level, upUserId, upChannelId,
					tagKey, tags, count, offset);

			return APIResponse.getNewSuccessResp(ret);
		}
	}

	/**
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号，用于鉴权
	 * @param type
	 *            （选填，默认不参与查询）</br>
	 *            内容类型（可空）
	 * @param status
	 *            （选填，默认不参与查询）</br>
	 *            内容状态
	 * @param level
	 *            （选填，默认不参与查询）</br>
	 *            内容等级
	 * @param upUserId
	 *            （选填，默认不参与查询）</br>
	 *            上传者的用户编号
	 * @param upChannelId
	 *            （选填，默认不参与查询）</br>
	 *            上传专栏的专栏编号
	 * @param tagKey
	 *            标签分组名称
	 * @param tags
	 *            标签列表（JSON数组格式）
	 * @param count
	 *            （选填，默认10）</br>
	 *            分页读取的记录数量
	 * @param offset
	 *            （选填，默认0）</br>
	 *            分页读取的起点位置
	 * 
	 * @return 内容对象数组
	 */
	@POSTAPI(path = "searchContents")
	public APIResponse searchContents(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		Byte type = Param.getByteDFLT(c, "type", null);
		Byte status = Param.getByteDFLT(c, "status", null);
		Byte level = Param.getByteDFLT(c, "level", null);
		Long upUserId = Param.getLongDFLT(c, "upUserId", null);
		Long upChannelId = Param.getLongDFLT(c, "upChannelId", null);

		String keywords = Param.getString(c, "keywords");

		Integer count = Param.getIntegerDFLT(c, "count", 10);
		Integer offset = Param.getIntegerDFLT(c, "offset", 0);

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			List<Content> ret = contentService.searchContents(conn, appId, type, status, level, upUserId, upChannelId,
					keywords, count, offset);

			return APIResponse.getNewSuccessResp(ret);
		}
	}

	/**
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号，用于鉴权
	 * @param contentId
	 *            内容编号
	 * 
	 * @return 内容对象
	 */
	@POSTAPI(path = "getContentById")
	public APIResponse getContentById(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");
		Long contentId = Param.getLong(c, "contentId");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			Content ret = contentService.getContentById(conn, contentId);

			return APIResponse.getNewSuccessResp(Param.checkNull(ret));
		}
	}

	/**
	 * 根据内容编号和标签分组名称，获取该内容的标签列表
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号
	 * @param contentId
	 *            内容编号
	 * @param tagKey
	 *            标签分组名称
	 * @return 内容对应标签分组的标签数组，JSONArray格式
	 */
	@POSTAPI(path = "getContentTags")
	public APIResponse getContentTags(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		Long contentId = Param.getLong(c, "contentId");
		String tagKey = Param.getString(c, "tagKey");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			JSONArray ret = contentService.getContentTags(conn, contentId, tagKey);

			return APIResponse.getNewSuccessResp(ret);
		}
	}

	/**
	 * 返回应用对应的全部内容标签
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号，用于鉴权
	 * @return 标签的JSON对象，例如</br>
	 * 
	 */
	@POSTAPI(path = "getAllTags")
	public APIResponse getAllTags(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			JSONObject ret = contentService.getAllTags(conn, appId);

			return APIResponse.getNewSuccessResp(ret);
		}
	}

	/**
	 * 获取内容对应的数据源
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号，用于鉴权
	 * @param contentId
	 *            内容编号
	 * @param sourceKey
	 *            来源关键字，如youku，tudou等
	 * @return ContentSource 内容对应的数据源
	 */
	@POSTAPI(path = "getContentSource")
	public APIResponse getContentSource(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		Long contentId = Param.getLong(c, "contentId");
		String sourceKey = Param.getString(c, "sourceKey");

		try (DruidPooledConnection conn = (DruidPooledConnection) dsRds.openConnection()) {
			App app = ServiceUtils.appAuth(conn, appId);// app鉴权
			User user = ServiceUtils.userAuth(conn, userId);// user鉴权

			ContentSource cs = contentService.getContentSource(conn, contentId, sourceKey);
			return APIResponse.getNewSuccessResp(Param.checkNull(cs));
		}
	}

	/**
	 * 获取内容对应的数据源
	 * 
	 * @param appId
	 *            应用编号
	 * @param userId
	 *            用户编号，用于鉴权
	 * @param contentId
	 *            内容编号
	 * @param source
	 *            内容源JSON字符串
	 */
	// TODO todo
	public APIResponse setContentSource(APIRequest req) throws Exception {
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		Long contentId = Param.getLong(c, "contentId");
		String source = Param.getString(c, "source");

		return APIResponse.getNewFailureResp(BaseRC.NOT_FINISHED);
	}
}
