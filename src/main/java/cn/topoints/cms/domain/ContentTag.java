package cn.topoints.cms.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 
 * 内容的标签
 */
@RDSAnnEntity(alias = "tb_content_tag")
public class ContentTag {

	/**
	 * 应用编号
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 标签名称
	 */
	@RDSAnnID
	@RDSAnnField(column = "VARCHAR(64)")
	public String tagKey;

	/**
	 * 标签名称
	 */
	@RDSAnnID
	@RDSAnnField(column = "VARCHAR(64)")
	public String name;

}
