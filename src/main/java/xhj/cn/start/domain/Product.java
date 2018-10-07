package xhj.cn.start.domain;

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
	public Long productId;
	/**
	 * 商品名称
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String productName;
	/**
	 * 商品价格
	 */
	@RDSAnnField(column = RDSAnnField.DOUBLE)
	public Double productPrice;
	/**
	 * 商品类型
	 */
	@RDSAnnField(column = "VARCHAR(32)")
	public String productType;
	/**
	 * 商品描述
	 */
	@RDSAnnField(column = RDSAnnField.TEXT)
	public String productMessage;
	/**
	 * 商品库存
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer productRepertory;
	/**
	 * 是否精选（是1/否0）
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer productSift;
	/**
	 * 是否预售（是1/否0）
	 */
	@RDSAnnField(column = RDSAnnField.INTEGER)
	public Integer productPresell;


}
