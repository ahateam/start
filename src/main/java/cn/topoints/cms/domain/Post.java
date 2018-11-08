package cn.topoints.cms.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 帖子实体
 *
 */
@RDSAnnEntity(alias = "tb_post")
public class Post {

	public static final Byte TYPE_ARTICLE = 0;// 文章
	public static final Byte TYPE_GALLERY = 1;// 相册
	public static final Byte TYPE_ALBUM = 2;// 影集

	public static final Byte STATUS_DRAFT = 0; // 草稿
	public static final Byte STATUS_NORMAL = 1; // 正常
	public static final Byte STATUS_CLOSED = 2; // 已关闭
	public static final Byte STATUS_DELETED = 3; // 已删除

	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	/**
	 * 所属app的编号</br>
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 类型
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte type;

	/**
	 * 状态
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte status;

	/**
	 * 分级（用于权限控制）</br>
	 * （未实现）
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte level;

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
	 * 上传用户编号
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long upUserId;

	/**
	 * 上传专栏编号
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long upChannelId;

	/**
	 * 标题
	 */
	@RDSAnnField(column = "VARCHAR(128)")
	public String title;

	/**
	 * 内容信息（JSON）
	 */
	@RDSAnnField(column = RDSAnnField.TEXT)
	public String data;

	/**
	 * 展示信息（JSON）
	 */
	@RDSAnnField(column = RDSAnnField.SHORT_TEXT)
	public String view;
}
