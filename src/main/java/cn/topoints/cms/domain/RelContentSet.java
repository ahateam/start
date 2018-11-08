package cn.topoints.cms.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 用户与内容的关系表（创建发布）
 *
 */
@RDSAnnEntity(alias = "tb_rel_content_set")
public class RelContentSet {

	/**
	 * 内容集合编号，ID列，无需索引
	 */
	@RDSAnnID
	@RDSAnnField(column = "VARCHAR(64)")
	public String setKey;
	
	/**
	 * 所属app的编号</br>
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 内容编号，ID列，无需索引
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long contentId;

	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date createTime;

	/**
	 * 排序权重，0~1000</br>
	 * 越小排序越靠前
	 */
	@RDSAnnField(column = RDSAnnField.SHORT)
	public Integer weight;

}
