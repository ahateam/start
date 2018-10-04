package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;
import cn.topoints.utils.data.rds.RDSAnnIndex;

@RDSAnnEntity(alias = "tb_store")
public class Store {

	/**
	 * 门店ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;
	
	/**
	 * 所属app的编号</br>
	 * (最好建索引)
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 门店poi_id
	 */
	@RDSAnnField(column = "VARCHAR(64)")
	public String poiId;

	/**
	 * 门店名称
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String name;

	/**
	 * 门店所在省份
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String province;

	/**
	 * 门店所在城市
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String city;

	/**
	 * 获取二维码参数
	 */
	@RDSAnnField(column = "VARCHAR(64)")
	public String ticket;

}
