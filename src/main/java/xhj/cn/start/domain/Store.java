package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

@RDSAnnEntity(alias = "tb_store_message")
public class Store {
	
	/**
	 * 门店ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long storeId;
	
	/**
	 * 门店poi_id
	 */
	@RDSAnnID
	@RDSAnnField(column = "VARCHAR(64)")
	public String storePoiId;
	
	/**
	 * 总店名
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String storeBusinessName;
	
	/**
	 * 门店所在省份
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String storeProvince;
	
	/**
	 * 门店所在城市
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String storeCity;
	
	/**
	 * 分店名
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String storeBranchName;
	
	/**
	 * 获取二维码参数
	 */
	@RDSAnnField(column = "VARCHAR(64)")
	public String ticket;
	
	

}
