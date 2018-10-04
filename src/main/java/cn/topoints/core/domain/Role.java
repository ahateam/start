package cn.topoints.core.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;
import cn.topoints.utils.data.rds.RDSAnnIndex;

/**
 * 权限模块，角色表</br>
 * TODO 待实现
 *
 */
@RDSAnnEntity(alias = "tb_role")
public class Role {

	/**
	 * 角色编号
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;

	/**
	 * 所属app的编号</br>
	 * (内容很多，最好建索引)
	 */
	@RDSAnnIndex
	@RDSAnnField(column = RDSAnnField.ID)
	public Long appId;

	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date createDate;

	/**
	 * 角色名称
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String name;
}
