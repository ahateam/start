package xhj.cn.start.repository;

import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;

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
	 * @描述 根据对应参数拉取商品信息
	 * @param conn
	 * @param all
	 * @return
	 * @throws Exception
	 */
	public Product getStoreByKeys(DruidPooledConnection conn,List all) throws Exception {
		return getByKeys(conn, (String[])all.get(0), (Object[])all.get(1));
	}
	
	/**
	 * @描述 获取所有商品列表
	 * @param conn
	 * @param count
	 * @param offset
	 * @return
	 * @throws ServerException
	 */
	public List<Product> getAllStoreList(DruidPooledConnection conn,Integer count,Integer offset) throws Exception{
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
	public List<Product> getStoreList(DruidPooledConnection conn,List all,Integer count,Integer offset) throws Exception{
		return getListByKeys(conn, (String[])all.get(0), (Object[])all.get(1), count, offset);
	}
	
	/**
	 * @描述 根据要求修改商品对应信息
	 * @param conn
	 * @param store
	 * @throws Exception
	 */
	public void updateStore(DruidPooledConnection conn,List all, Product product) throws Exception {
		updateByKeys(conn, (String[])all.get(0), (Object[])all.get(1), product, true);
	}

}
