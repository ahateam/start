package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;
import cn.topoints.utils.data.rds.RDSAnnIndex;

@RDSAnnEntity(alias = "tb_store_saler")
public class StoreSaler {

	/**
	 * 所属门店ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long storeId;

	/**
	 * 营业员ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	/**
	 * 所属app的编号</br>
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 营业员名称
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String name;

	/**
	 * 营业员所属门店对应poi_id
	 */
	@RDSAnnField(column = "VARCHAR(64)")
	public String poiId;

	/**
	 * 获取二维码对应参数
	 */
	@RDSAnnField(column = "VARCHAR(64)")
	public String ticket;

}
