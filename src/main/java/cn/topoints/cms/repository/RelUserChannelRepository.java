package cn.topoints.cms.repository;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.cms.domain.RelUserChannel;
import cn.topoints.utils.data.rds.RDSRepository;

public class RelUserChannelRepository extends RDSRepository<RelUserChannel> {

	private static RelUserChannelRepository ins;

	public static synchronized RelUserChannelRepository getInstance() {
		if (null == ins) {
			ins = new RelUserChannelRepository();
		}
		return ins;
	}

	private RelUserChannelRepository() {
		super(RelUserChannel.class);
	}

	public RelUserChannel getByUserIdAndChannelId(DruidPooledConnection conn, Long userId, Long channelId)
			throws Exception {
		return get(conn, "WHERE user_id=? AND channel_id=?", new Object[] { userId, channelId });
	}

	public int deleteByUserIdAndChannelId(DruidPooledConnection conn, Long userId, Long channelId) throws Exception {
		return delete(conn, "WHERE user_id=? AND channel_id=?", new Object[] { userId, channelId });
	}

}
