package cn.topoints.cms.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.cms.domain.Content;
import cn.topoints.cms.domain.ContentSet;
import cn.topoints.utils.data.rds.RDSRepository;

public class ContentSetRepository extends RDSRepository<ContentSet> {

	private static ContentSetRepository ins;

	public static synchronized ContentSetRepository getInstance() {
		if (null == ins) {
			ins = new ContentSetRepository();
		}
		return ins;
	}

	private ContentSetRepository() {
		super(ContentSet.class);
	}

	public List<Content> getContentsBySetKey(DruidPooledConnection conn, ContentRepository contentRepository,
			String setKey, Long appId, Integer conut, Integer offset) throws Exception {

		// 在ContentSet的repository中尝试返回Content对象，则需要使用repository基类提供的native的跨对象方法
		return nativeGetList(conn, contentRepository,
				"SELECT * from tb_content where tb_content.id = any(select tr.content_id  from (select * from tb_rel_content_set where set_key = ? and app_id = ? order by weight limit ?,?)as tr);",
				new Object[] { setKey, appId, offset, conut });
	}

}
