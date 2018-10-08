package xhj.cn.start.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xhj.cn.start.repository.CoinRecordRepository;
import xhj.cn.start.repository.CoinRepository;
import xhj.cn.start.repository.OrderRepository;
import xhj.cn.start.util.GetDomain;

public class OrderService {
	
	private static Logger log = LoggerFactory.getLogger(StoreService.class);

	private static OrderService ins;

	public static synchronized OrderService getInstance() {
		if (null == ins) {
			ins = new OrderService();
		}
		return ins;
	}

	private CoinRepository coinRepository;
	private CoinRecordRepository coinRecordRepository;
	private OrderRepository orderRepository;
	private GetDomain gd = new GetDomain();

	private OrderService() {
		try {
			coinRepository = CoinRepository.getInstance();
			coinRecordRepository = CoinRecordRepository.getInstance();
			orderRepository = OrderRepository.getInstance();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	

}
