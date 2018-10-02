package cn.topoints.utils.data;

import cn.topoints.utils.api.ServerException;

/**
 */
public interface DataSource {

	public Object openConnection() throws ServerException;

	public void closeConnection(Object conn) throws ServerException;
}
