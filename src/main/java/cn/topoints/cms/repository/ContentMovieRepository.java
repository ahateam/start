package cn.topoints.cms.repository;

import cn.topoints.cms.domain.ContentMovie;
import cn.topoints.utils.data.rds.RDSRepository;

public class ContentMovieRepository extends RDSRepository<ContentMovie> {

	private static ContentMovieRepository ins;

	public static synchronized ContentMovieRepository getInstance() {
		if (null == ins) {
			ins = new ContentMovieRepository();
		}
		return ins;
	}

	private ContentMovieRepository() {
		super(ContentMovie.class);
	}


}
