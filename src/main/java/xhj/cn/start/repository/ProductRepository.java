package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;

import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.rds.RDSRepository;
import xhj.cn.start.domain.Product;

public class ProductRepository extends RDSRepository<Product> {
	
	private static ProductRepository ins;

	public static synchronized ProductRepository getInstance() {
		if (null == ins) {
			ins = new ProductRepository();
		}
		return ins;
	}
	
	private ProductRepository() {
		super(Product.class);
	}
	
	/**
	 * @描述 根据商品ID拉取商品信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Product getProductById(DruidPooledConnection conn,Long id) throws Exception {
		return get(conn, "where id = ?", new Object[] {id});
	}
	
	/**
	 * @描述 获取所有商品列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Product> getAllProductList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
		return getList(conn, null, null, count, offset);
	}
	
	/**
	 * @描述 根据对应要求获取商品列表
	 * @param conn
	 * @param all
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Product> getProductList(DruidPooledConnection conn,List all,Integer count,Integer offset) throws Exception{
		return getListByKeys(conn, (String[])all.get(0), (Object[])all.get(1), count, offset);
	}
	
	/**
	 * @描述 根据要求修改商品对应信息
	 * @param conn
	 * @param store
	 * @throws Exception
	 */
	public void updateProduct(DruidPooledConnection conn,List all, Product product) throws Exception {
		updateByKeys(conn, (String[])all.get(0), (Object[])all.get(1), product, true);
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
	public List<Product> getProductListByTags(DruidPooledConnection conn,String tagKey, 
			JSONArray tags,Integer count,Integer offset) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("WHERE ");
		sb.append("JSON_CONTAINS(tags->'$.").append(tagKey).append("', '");
		for (Object tag : tags) {
			sb.append("\"").append(tag).append("\",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("')");
		return getList(conn, sb.toString(), null, count, offset);
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
		return getList(conn, "WHERE title like '%"+title+"%'", null, count, offset);
	}

}
