package cn.topoints.core.repository;

import cn.topoints.core.domain.Valid;
import cn.topoints.utils.data.ots.OTSRepository;

public class ValidRepository extends OTSRepository<Valid> {

	private static ValidRepository ins;

	public static synchronized ValidRepository getInstance() {
		if (null == ins) {
			ins = new ValidRepository();
		}
		return ins;
	}

	private ValidRepository() {
		super(Valid.class);
	}

}