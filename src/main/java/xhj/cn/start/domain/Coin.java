package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 金币（积分）
 *
 */
@RDSAnnEntity(alias = "tb_coin")
public class Coin {
	/**
	 * 对应用户ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;
	/**
	 * 积分值
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer integral;

}
