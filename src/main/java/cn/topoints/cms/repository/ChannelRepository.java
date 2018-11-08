package cn.topoints.cms.repository;

import cn.topoints.cms.domain.Channel;
import cn.topoints.utils.data.rds.RDSRepository;

public class ChannelRepository extends RDSRepository<Channel> {

	private static ChannelRepository ins;

	public static synchronized ChannelRepository getInstance() {
		if (null == ins) {
			ins = new ChannelRepository();
		}
		return ins;
	}

	private ChannelRepository() {
		super(Channel.class);
	}

}
