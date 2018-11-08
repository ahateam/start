package cn.topoints.cms.repository;

import cn.topoints.cms.domain.RelContentSet;
import cn.topoints.utils.data.rds.RDSRepository;

public class RelContentSetRepository extends RDSRepository<RelContentSet> {

	private static RelContentSetRepository ins;

	public static synchronized RelContentSetRepository getInstance() {
		if (null == ins) {
			ins = new RelContentSetRepository();
		}
		return ins;
	}

	private RelContentSetRepository() {
		super(RelContentSet.class);
	}

}
