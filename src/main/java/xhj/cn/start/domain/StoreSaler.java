package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;
import cn.topoints.utils.data.rds.RDSAnnIndex;

@RDSAnnEntity(alias = "tb_store_saler")
public class StoreSaler {

	public static final Byte LEVEL_1 = 1;
	public static final Byte LEVEL_2 = 2;
	public static final Byte LEVEL_3 = 3;
	
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
	 * 店员等级，只支持3级
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte level;

	/**
	 * 上级店员编号</br>
	 * 带索引
	 */
	@RDSAnnIndex
	@RDSAnnField(column = RDSAnnField.ID)
	public Long leaderId;

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
	
	/**
	 * 牛逼的JSON</br>
	 * 数据未定
	 */
	@RDSAnnField(column = RDSAnnField.JSON)
	public String tags;

}
