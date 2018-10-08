package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 用户保存地址
 *
 */
public class Address {
	/**
	 * ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;
	/**
	 * 用户ID
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;
	/**
	 * 订单发货省份
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String province;
	/**
	 * 订单发货城市
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String city;
	/**
	 * 地址
	 */
	@RDSAnnField(column = "VARCHAR(128)")
	public String address;

}
