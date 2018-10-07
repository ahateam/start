package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnField;

public class ProductOrder {

	public Long productId;

	/**
	 * 积分记录编号
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long coinRecordId;
}
