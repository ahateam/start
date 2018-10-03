package xhj.cn.start.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

@RDSAnnEntity(alias = "tb_manager")
public class Manager {
	
	/**
	 * 管理员ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	private Long manager_id;
	
	/**
	 * 管理员账号
	 */
	@RDSAnnID
	@RDSAnnField(column = "VARCHAR(64)")
	private String manager_num;
	
	/**
	 * 管理员登录密码
	 */
	@RDSAnnField(column = "VARCHAR(64)")
	private String manager_password;

}
