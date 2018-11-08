package cn.topoints.cms.repository;

import cn.topoints.cms.domain.ContentTag;
import cn.topoints.utils.data.rds.RDSRepository;

public class ContentTagRepository extends RDSRepository<ContentTag> {

	private static ContentTagRepository ins;

	public static synchronized ContentTagRepository getInstance() {
		if (null == ins) {
			ins = new ContentTagRepository();
		}
		return ins;
	}

	private ContentTagRepository() {
		super(ContentTag.class);
	}

}
