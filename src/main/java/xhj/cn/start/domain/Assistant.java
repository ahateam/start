package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

@RDSAnnEntity(alias = "tb_assistant_message")
public class Assistant {
	
	/**
	 * 营业员ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long assistantId;
	
	/**
	 * 营业员名称
	 */
	@RDSAnnField(column = "VARCHAR(16)")
	public String assistantName;
	
	/**
	 * 营业员所属门店对应poi_id
	 */
	@RDSAnnField(column = "VARCHAR(64)")
	public String assistantStorePoiId;
	
	/**
	 * 获取二维码对应参数
	 */
	@RDSAnnField(column = "VARCHAR(64)")
	public String ticket;

}
