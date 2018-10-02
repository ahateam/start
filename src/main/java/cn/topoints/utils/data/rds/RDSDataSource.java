package cn.topoints.utils.data.rds;

import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.api.BaseRC;
import cn.topoints.utils.api.ServerException;
import cn.topoints.utils.data.DataSource;

public class RDSDataSource implements DataSource {

	private DruidDataSource dds;

	public RDSDataSource(Properties props) throws Exception {
		dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(props);
	}

	@Override
	public Object openConnection() throws ServerException {
		try {
			return dds.getConnection();
		} catch (SQLException e) {
			throw new ServerException(BaseRC.REPOSITORY_CONNECTION_ERROR, e.getMessage());
		}
	}

	@Override
	public void closeConnection(Object conn) throws ServerException {
		try {
			((DruidPooledConnection) conn).close();
		} catch (Exception e) {
			throw new ServerException(BaseRC.REPOSITORY_CONNECTION_ERROR, e.getMessage());
		}
	}

}
