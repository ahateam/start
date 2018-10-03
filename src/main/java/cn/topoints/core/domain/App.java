package cn.topoints.core.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 应用实体</br>
 * 一套后台可以跑多个应用。。。</br>
 * 每个应用有自己独立的标签组</br>
 */
@RDSAnnEntity(alias = "tb_app")
public class App {

	public static final Byte STATUS_PENDING = -1; // 待审核
	public static final Byte STATUS_NORMAL = 0; // 正常
	public static final Byte STATUS_STOPED = 1; // 停止使用
	public static final Byte STATUS_DELETED = 2; // 删除

	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	/**
	 * 应用名称</br>
	 * (可以重复)
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String name;

	/**
	 * 状态（暂时没有作用）
	 */
	@RDSAnnField(column = RDSAnnField.BYTE)
	public Byte status;

	/**
	 * 应用所属的用户编号</br>
	 * （目前没有开放用户自行注册应用，由系统创建）
	 */
	@RDSAnnField(column = RDSAnnField.ID)
	public Long userId;

	/**
	 * App的加密密钥
	 */
	@RDSAnnField(column = "VARCHAR(128)")
	public String secret;
}
