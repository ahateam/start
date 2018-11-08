package cn.topoints.cms.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.topoints.cms.domain.Content;
import cn.topoints.cms.domain.ContentSource;
import cn.topoints.cms.domain.ContentTag;
import cn.topoints.cms.repository.ContentRepository;
import cn.topoints.cms.repository.ContentSourceRepository;
import cn.topoints.cms.repository.ContentTagRepository;
import cn.topoints.utils.IDUtils;
import cn.topoints.utils.api.BaseRC;
import cn.topoints.utils.api.ServerException;

public class ContentService {

	private static Logger log = LoggerFactory.getLogger(ContentService.class);

	private static ContentService ins;

	public static synchronized ContentService getInstance() {
		if (null == ins) {
			ins = new ContentService();
		}
		return ins;
	}

	private ContentRepository contentRepository;
	private ContentTagRepository contentTagRepository;
	private ContentSourceRepository contentSourceRepository;

	private ContentService() {
		try {
			contentRepository = ContentRepository.getInstance();
			contentTagRepository = ContentTagRepository.getInstance();
			contentSourceRepository = ContentSourceRepository.getInstance();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 验证内容是否合法：判断是否下线，等等。TODO 目前待完善
	 */
	public Content auth(DruidPooledConnection conn, Long contentId) throws Exception {
		// 先判断user是否存在
		Content content = contentRepository.getByKey(conn, "id", contentId);
		if (null == content) {
			// content不存在
			throw new ServerException(BaseRC.CMS_CONTENT_NOT_EXISET);
		} else {
			// 再判断content状态是否有效，TODO 目前status没有启用
			return content;
		}
	}

	/**
	 * 创建内容
	 */
	public Content createContent(DruidPooledConnection conn, Long appId, Byte type, Byte level, Long upUserId,
			Long upChannelId, String title, Byte score, String origin, String meta, String data, String secretData,
			String view) throws Exception {

		Content c = new Content();

		c.id = IDUtils.getSimpleId();

		c.appId = appId;
		c.type = type;
		c.status = Content.STATUS_DRAFT;// 首次创建时是草稿状态
		c.level = level;

		c.createTime = new Date();
		c.updateTime = c.createTime;
		c.upUserId = upUserId;
		c.upChannelId = upChannelId;

		c.title = title;
		c.score = score;
		c.origin = origin;
		c.meta = meta;
		c.data = data;
		c.secretData = secretData;
		c.view = view;
		c.tags = "{}";// 设置为JSON数组的空格式，否则后续的编辑操作会没效果（可能是MYSQL的bug）

		contentRepository.insert(conn, c);

		return c;
	}

	/**
	 * 根据关键字搜索内容
	 */
	public List<Content> searchContents(DruidPooledConnection conn, Long appId, Byte type, Byte status, Byte level,
			Long upUserId, Long upChannelId, String keywords, Integer count, Integer offset) throws Exception {
		return contentRepository.searchContents(conn, appId, type, status, level, upUserId, upChannelId, keywords,
				count, offset);
	}

	/**
	 * 根据标签查询内容
	 */
	public List<Content> queryContents(DruidPooledConnection conn, Long appId, Byte type, Byte status, Byte level,
			Long upUserId, Long upChannelId, String tagKey, JSONArray tags, Integer count, Integer offset)
			throws Exception {
		return contentRepository.queryContents(conn, appId, type, status, level, upUserId, upChannelId, tagKey, tags,
				count, offset);
	}

	/**
	 * 根据内容编号查询内容
	 */
	public Content getContentById(DruidPooledConnection conn, Long contentId) throws Exception {
		return contentRepository.getByKey(conn, "id", contentId);
	}

	/**
	 * 根据编号删除内容
	 */
	public void deleteContent(DruidPooledConnection conn, Long contentId) throws Exception {
		contentRepository.deleteByKey(conn, "id", contentId);
	}

	/**
	 * 读取内容对应的标签
	 * 
	 */
	public JSONArray getContentTags(DruidPooledConnection conn, Long contentId, String tagKey) throws Exception {
		return contentRepository.getContentTags(conn, contentId, tagKey);
	}

	/**
	 * 为内容添加标签
	 */
	public void addContentTags(DruidPooledConnection conn, Long contentId, String tagKey, JSONArray tags)
			throws Exception {
		contentRepository.addContentTags(conn, contentId, tagKey, tags);
	}

	/**
	 * 移除内容的标签
	 */
	public void removeContentTags(DruidPooledConnection conn, Long contentId, String tagKey, JSONArray tags)
			throws Exception {
		contentRepository.removeContentTags(conn, contentId, tagKey, tags);
	}

	/**
	 * 创建标签
	 */
	public ContentTag createContentTag(DruidPooledConnection conn, Long appId, String tagKey, String name)
			throws Exception {
		ContentTag ct = new ContentTag();

		ct.appId = appId;
		ct.tagKey = tagKey;
		ct.name = name;

		contentTagRepository.insert(conn, ct);

		return ct;
	}

	/**
	 * 获得某应用内容对应的全部标签</br>
	 * 并以key对应的JSON对象数组形式返回，例如：</br>
	 * {tag:["动作","喜剧"],actor:["刘烨","章子怡"]}
	 */
	public JSONObject getAllTags(DruidPooledConnection conn, Long appId) throws Exception {
		List<ContentTag> tags = contentTagRepository.getListByKey(conn, "app_id", appId, 512, 0);

		JSONObject ret = new JSONObject();
		for (ContentTag t : tags) {
			JSONArray ja = ret.getJSONArray(t.tagKey);
			if (null == ja) {
				ja = new JSONArray();
				ja.add(t.name);
				ret.put(t.tagKey, ja);
			} else {
				ja.add(t.name);
			}
		}
		return ret;
	}

	public void createContentSource(DruidPooledConnection conn, Long contentId, String sourceKey, String source)
			throws Exception {
		ContentSource cs = new ContentSource();
		cs.contentId = contentId;
		cs.sourceKey = sourceKey;
		cs.source = source;

		contentSourceRepository.insert(conn, cs);
	}

	public ContentSource getContentSource(DruidPooledConnection conn, Long contentId, String sourceKey)
			throws Exception {
		String[] keys = { "content_id", "source_key" };
		Object[] values = { contentId, sourceKey };
		return contentSourceRepository.getByKeys(conn, keys, values);
	}

}
