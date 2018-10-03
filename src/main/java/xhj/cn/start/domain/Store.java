package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

@RDSAnnEntity(alias = "tb_store_message")
public class Store {
	
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long storeId;
	
	@RDSAnnID
	@RDSAnnField(column = "VARCHAR(64)")
	public String storePoiId;
	
	@RDSAnnField(column = "VARCHAR(32)")
	public String storeBusinessName;
	
	@RDSAnnField(column = "VARCHAR(32)")
	public String storeBranchName;
	
	@RDSAnnField(column = "VARCHAR(64)")
	public String ticket;
	
	

}
