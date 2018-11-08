package cn.topoints.cms.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 频道（专栏）实体
 *
 */
@RDSAnnEntity(alias = "tb_channel")
public class Channel {

	public static final Byte STATUS_NORMAL = 0; // 正常
	public static final Byte STATUS_STOP = 1; // 已停止更新
	public static final Byte STATUS_CLOSED = 2; // 已关闭禁用
	public static final Byte STATUS_DELETED = 3; // 已删除

	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	/**
	 * 所属app的编号</br>
	 * （表不大，无需建索引）
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 状态（暂时没有作用）
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
	 * 描述
	 */
	@RDSAnnField(column = RDSAnnField.SHORT_TEXT)
	public String description;

	/**
	 * 展示信息（JSON）
	 */
	@RDSAnnField(column = RDSAnnField.SHORT_TEXT)
	public String view;

}
