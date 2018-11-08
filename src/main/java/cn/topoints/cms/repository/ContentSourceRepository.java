package cn.topoints.cms.repository;

import cn.topoints.cms.domain.ContentSource;
import cn.topoints.utils.data.rds.RDSRepository;

public class ContentSourceRepository extends RDSRepository<ContentSource> {

	private static ContentSourceRepository ins;

	public static synchronized ContentSourceRepository getInstance() {
		if (null == ins) {
			ins = new ContentSourceRepository();
		}
		return ins;
	}

	private ContentSourceRepository() {
		super(ContentSource.class);
	}

}
