package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

@RDSAnnEntity(alias = "tb_assistant_message")
public class Assistant {
	
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long assistantId;
	
	@RDSAnnField(column = "VARCHAR(16)")
	public String assistantName;
	
	@RDSAnnField(column = "VARCHAR(64)")
	public String assistantStorePoiId;
	
	@RDSAnnField(column = "VARCHAR(64)")
	public String ticket;

}
