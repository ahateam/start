package cn.topoints.cms.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 
 * 内容集合实体类</br>
 * 例如首页中出现的人工编辑的内容区块</br>
 * 
 */
@RDSAnnEntity(alias = "tb_content_set")
public class ContentSet {

	public static final Byte STATUS_NORMAL = 0; // 正常
	public static final Byte STATUS_CLOSED = 1; // 已关闭
	public static final Byte STATUS_DELETED = 2; // 已删除

	/**
	 * 直接用setkey作为唯一id
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
	 * 状态
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte status;

	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date createTime;

	/**
	 * 更新时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date updateTime;

	/**
	 * 标题
	 */
	@RDSAnnField(column = "VARCHAR(128)")
	public String title;

	/**
	 * 展示数据，例如缩略图等其它展示相关的信息
	 */
	@RDSAnnField(column = RDSAnnField.SHORT_TEXT)
	public String view;
}
