package xhj.cn.start.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;

import cn.topoints.utils.IDUtils;
import cn.topoints.utils.api.ServerException;
import xhj.cn.start.domain.Product;
import xhj.cn.start.repository.ProductRepository;
import xhj.cn.start.util.GetDomain;

public class ProductService {
	
	private static Logger log = LoggerFactory.getLogger(StoreService.class);

	private static ProductService ins;

	public static synchronized ProductService getInstance() {
		if (null == ins) {
			ins = new ProductService();
		}
		return ins;
	}
	
	private ProductRepository productRepository;
	private GetDomain gd = new GetDomain();
	
	private ProductService() {
		try {
			productRepository = ProductRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @描述 根据商品ID拉取商品信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Product getProductById(DruidPooledConnection conn, Long id) throws Exception {
		return productRepository.getProductById(conn, id);
	}
	
	/**
	 * @描述 根据商品类型拉取商品列表
	 * @param conn
	 * @param title
	 * @param type
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Product> getProductByType(DruidPooledConnection conn,String type,Integer count,Integer offset) throws Exception {
		String key = "type";
		Object[] value = { type };
		return productRepository.getListByKey(conn, key, value,count,offset);
	}
	
	/**
	 * @描述 根据标签查询商品列表
	 * @param conn
	 * @param tagKey
	 * @param tags
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Product> getProductByTags(DruidPooledConnection conn,String tagKey, 
			JSONArray tags,Integer count,Integer offset) throws Exception{
		return productRepository.getProductListByTags(conn, tagKey, tags, count, offset);
	}
	
	/**
	 * @描述 根据商品标题模糊查询
	 * @param conn
	 * @param title
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Product> getProductLikeTitle(DruidPooledConnection conn,String title,Integer count,Integer offset) throws Exception{
		return productRepository.getProductLikeTitle(conn, title, count, offset);
	}
	
	/**
	 * @描述 获取所有商品列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public List<Product> getAllProduct(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return productRepository.getAllProductList(conn, count, offset);
	}
	
	/**
	 * @描述 添加商品
	 * @param conn
	 * @param title
	 * @param price
	 * @param type
	 * @param discription
	 * @param amount
	 * @param tags
	 * @throws Exception
	 */
	public void setProduct(DruidPooledConnection conn,String title,Double price,String type,
			String discription,Integer amount,String tags) throws Exception {
		Product p = new Product();
		p.id = IDUtils.getSimpleId();
		p.title = title;
		p.price = price;
		p.type = type;
		p.discription = discription;
		p.amount = amount;
		p.remainingAmount = amount;
		p.createTime = new Date();
		p.tags = tags;
		productRepository.insert(conn, p);
	}
	
	/**
	 * @描述 根据商品ID删除商品
	 * @param conn
	 * @param id
	 * @throws ServerException
	 */
	public void delProductById(DruidPooledConnection conn,Long id) throws ServerException {
		productRepository.deleteByKey(conn, "id", new Object[] {id});
	}
	
	/**
	 * @描述 根据ID修改商品信息/根据类型批量修改商品信息
	 * @param conn
	 * @param id
	 * @param type
	 * @param product
	 * @throws Exception
	 */
	public void updateProduct(DruidPooledConnection conn,Long id,String type,Product product) throws Exception {
		Product p = new Product();
		p.id = id;
		p.type = type;
		List<Object> all = gd.getDomain(p);
		productRepository.updateProduct(conn, all, product);
	}

}
