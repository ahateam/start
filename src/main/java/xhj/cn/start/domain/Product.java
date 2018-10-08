package xhj.cn.start.domain;

import java.util.Date;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 商品
 *
 */
@RDSAnnEntity(alias = "tb_product")
public class Product {

	/**
	 * 商品ID
	 */
	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long id;
	/**
	 * 标题
	 */
	@RDSAnnField(column = "VARCHAR(128)")
	public String title;
	/**
	 * 价格
	 */
	@RDSAnnField(column = RDSAnnField.DOUBLE)
	public Double price;
	/**
	 * 商品类型
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String type;
	/**
	 * 描述
	 */
	@RDSAnnField(column = RDSAnnField.TEXT)
	public String discription;
	/**
	 * 商品库存
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer amount;
	
	/**
	 * 商品剩余库存
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer remainingAmount;
	
	/**
	 * 创建时间
	 */
	@RDSAnnField(column = RDSAnnField.TIME)
	public Date createTime;
	
	/**
	 * 牛逼的JSON</br>
	 * store，对应门店信息</br>
	 * type，对应分类信息（虚拟，实物，落地页）</br>
	 */
	@RDSAnnField(column = RDSAnnField.JSON)
	public String tags;


}
