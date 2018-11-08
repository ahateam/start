package cn.topoints.cms.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.topoints.cms.domain.Content;
import cn.topoints.cms.domain.ContentSet;
import cn.topoints.cms.domain.RelContentSet;
import cn.topoints.cms.repository.ContentRepository;
import cn.topoints.cms.repository.ContentSetRepository;
import cn.topoints.cms.repository.RelContentSetRepository;
import cn.topoints.utils.api.BaseRC;
import cn.topoints.utils.api.ServerException;

public class ContentSetService {

	private static Logger log = LoggerFactory.getLogger(ContentSetService.class);

	private static ContentSetService ins;

	public static synchronized ContentSetService getInstance() {
		if (null == ins) {
			ins = new ContentSetService();
		}
		return ins;
	}

	private ContentSetRepository contentSetRepository;
	private RelContentSetRepository relContentSetRepository;

	private ContentRepository contentRepository;

	private ContentSetService() {
		try {
			contentSetRepository = ContentSetRepository.getInstance();
			relContentSetRepository = RelContentSetRepository.getInstance();

			contentRepository = ContentRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 创建内容集合
	 */
	public ContentSet createContentSet(DruidPooledConnection conn, String setKey, Long appId, String title, String view)
			throws Exception {
		ContentSet contentSet = contentSetRepository.getByKey(conn, "set_key", setKey);
		if (contentSet != null) {
			throw new ServerException(BaseRC.CMS_CONTENTSET_EXIST);
		} else {
			contentSet = new ContentSet();
			contentSet.setKey = setKey;
			contentSet.appId = appId;

			contentSet.status = ContentSet.STATUS_NORMAL;
			contentSet.createTime = new Date();
			contentSet.updateTime = contentSet.createTime;
			contentSet.title = title;
			contentSet.view = view;
			contentSetRepository.insert(conn, contentSet);
			return contentSet;
		}
	}

	public List<ContentSet> getContentSetsByAppId(DruidPooledConnection conn, Long appId, int count, int offset)
			throws Exception {
		return contentSetRepository.getListByKey(conn, "app_id", appId, count, offset);
	}

	public JSONArray putContentsInSet(DruidPooledConnection conn, String setKey, Long appId, JSONArray contents)
			throws Exception {
		ContentSet contentSet = contentSetRepository.getByKeys(conn, new String[] { "set_key", "app_id" },
				new Object[] { setKey, appId });
		if (contentSet == null) {
			throw new ServerException(BaseRC.CMS_CONTENT_NOT_EXISET);
		} else {
			JSONArray resultArray = new JSONArray();
			for (int i = 0; i < contents.size(); i++) {
				JSONObject jo = contents.getJSONObject(i);

				Long contentId = jo.getLong("contentId");
				Integer weight = jo.getInteger("weight");
				if (weight == null || weight < 0) {
					weight = 0;
				}

				RelContentSet relContentSet = relContentSetRepository.getByKeys(conn,
						new String[] { "set_key", "app_id", "content_id" }, new Object[] { setKey, appId, contentId });

				JSONObject resultObject = new JSONObject();
				try {

					if (relContentSet != null) {
						// 关联已经存在，更新weight即可
						relContentSet.weight = weight;

						relContentSetRepository.updateByKeys(conn, new String[] { "set_key", "app_id" },
								new Object[] { setKey, appId }, relContentSet, true);
					} else {
						// 创建新的关联
						relContentSet = new RelContentSet();
						// 三个主键
						relContentSet.setKey = setKey;
						relContentSet.appId = appId;
						relContentSet.contentId = contentId;

						relContentSet.createTime = new Date();
						relContentSet.weight = weight;

						relContentSetRepository.insert(conn, relContentSet);
					}
					resultObject.put("setKey", setKey);
					resultObject.put("appId", appId);
					resultObject.put("contentId", contentId);
					resultObject.put("result", true);
				} catch (Exception e) {
					resultObject.put("setKey", setKey);
					resultObject.put("appId", appId);
					resultObject.put("contentId", contentId);
					resultObject.put("result", false);
					resultObject.put("msg", e.getMessage());
				}
				resultArray.add(resultObject);
			}
			return resultArray;
		}
	}

	public JSONArray removeContentsInSet(DruidPooledConnection conn, String setKey, Long appId, JSONArray contentIds)
			throws Exception {
		ContentSet contentSet = contentSetRepository.getByKeys(conn, new String[] { "set_key", "app_id" },
				new Object[] { setKey, appId });
		if (contentSet == null) {
			throw new ServerException(BaseRC.CMS_CONTENT_NOT_EXISET);
		} else {
			JSONArray resultArray = new JSONArray();
			for (int i = 0; i < contentIds.size(); i++) {
				String contentId = contentIds.getString(i);
				JSONObject resultObject = new JSONObject();
				try {
					relContentSetRepository.deleteByKeys(conn, new String[] { "set_key", "app_id", "content_id" },
							new Object[] { setKey, appId, contentId });
					resultObject.put("setKey", setKey);
					resultObject.put("appId", appId);
					resultObject.put("contentId", contentId);
					resultObject.put("result", true);
				} catch (Exception e) {
					resultObject.put("setKey", setKey);
					resultObject.put("appId", appId);
					resultObject.put("contentId", contentId);
					resultObject.put("result", false);
					resultObject.put("msg", e.getMessage());
				}
				resultArray.add(resultObject);
			}
			return resultArray;
		}
	}

	public JSONArray batchUpdateContentSetWeight(DruidPooledConnection conn, String setKey, Long appId,
			JSONArray contents) throws Exception {
		ContentSet contentSet = contentSetRepository.getByKeys(conn, new String[] { "set_key", "app_id" },
				new Object[] { setKey, appId });
		if (contentSet == null) {
			throw new ServerException(BaseRC.CMS_CONTENT_NOT_EXISET);
		} else {
			JSONArray resultArray = new JSONArray();
			for (int i = 0; i < contents.size(); i++) {
				JSONObject jo = contents.getJSONObject(i);

				Long contentId = jo.getLong("contentId");
				Integer weight = jo.getInteger("weight");
				if (weight == null || weight < 0) {
					weight = 0;
				}

				RelContentSet relContentSet = relContentSetRepository.getByKeys(conn,
						new String[] { "set_key", "app_id", "content_id" }, new Object[] { setKey, appId, contentId });

				JSONObject resultObject = new JSONObject();
				try {
					if (relContentSet != null) {
						// 关联已经存在，更新weight即可
						relContentSet.weight = weight;

						relContentSetRepository.updateByKeys(conn, new String[] { "set_key", "app_id" },
								new Object[] { setKey, appId }, relContentSet, true);
						resultObject.put("setKey", setKey);
						resultObject.put("appId", appId);
						resultObject.put("contentId", contentId);
						resultObject.put("result", true);
					} else {
						resultObject.put("setKey", setKey);
						resultObject.put("appId", appId);
						resultObject.put("contentId", contentId);
						resultObject.put("result", false);
						resultObject.put("msg", "rel not exist");
					}

				} catch (Exception e) {
					resultObject.put("setKey", setKey);
					resultObject.put("appId", appId);
					resultObject.put("contentId", contentId);
					resultObject.put("result", false);
					resultObject.put("msg", e.getMessage());
				}
				resultArray.add(resultObject);
			}
			return resultArray;
		}
	}

	public List<Content> getContentsBySetKey(DruidPooledConnection conn, String setKey, Long appId, Integer count,
			Integer offset) throws Exception {
		return contentSetRepository.getContentsBySetKey(conn, contentRepository, setKey, appId, count, offset);
	}

}
