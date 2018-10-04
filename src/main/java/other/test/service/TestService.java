package other.test.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;

import cn.topoints.utils.data.DataSourceUtils;
import xhj.cn.start.domain.Assistant;
import xhj.cn.start.service.ChannelOpeService;

public class TestService {
	
	private static Logger log = LoggerFactory.getLogger(TestService.class);
	
	private static ChannelOpeService channelOpeService;
	private static DruidPooledConnection conn;
	
    @BeforeClass
	public static void before() {
		channelOpeService = ChannelOpeService.getInstance();
		DataSourceUtils.initDataSourceConfig();
		try {
			conn = (DruidPooledConnection) DataSourceUtils.getDataSource("rdsDefault").openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @AfterClass
	public static void after() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @Test
    public void test1() throws Exception {
    	Assistant assistant = channelOpeService.getAssistantByKeys(conn, 1L, null, null);
    	System.out.println(assistant.assistantName);
    	System.out.println(assistant.assistantStorePoiId);
    	System.out.println(assistant.ticket);
    	System.out.println(assistant.assistantId);
    }

}
