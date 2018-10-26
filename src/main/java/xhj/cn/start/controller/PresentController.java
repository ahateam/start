package xhj.cn.start.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.topoints.utils.api.http.Controller;
import cn.topoints.utils.data.DataSource;
import cn.topoints.utils.data.DataSourceUtils;
import xhj.cn.start.service.PresentService;

public class PresentController extends Controller {
	
	private static Logger log = LoggerFactory.getLogger(PresentController.class);

	private static PresentController ins;

	public static synchronized PresentController getInstance(String node) {
		if (null == ins) {
			ins = new PresentController(node);
		}
		return ins;
	}
	
	private PresentService presentService;
	private DataSource dsRds;
	
	private PresentController(String node) {
		super(node);
		try {
			presentService = PresentService.getInstance();
			dsRds = DataSourceUtils.getDataSource("rdsDefault");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	
}
