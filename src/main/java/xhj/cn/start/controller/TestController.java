package xhj.cn.start.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.topoints.utils.api.Param;
import cn.topoints.utils.api.http.APIRequest;
import cn.topoints.utils.api.http.APIResponse;
import cn.topoints.utils.api.http.Controller;

public class TestController extends Controller {

	private static Logger log = LoggerFactory.getLogger(TestController.class);

	private static TestController ins;

	public static synchronized TestController getInstance(String node) {
		if (null == ins) {
			ins = new TestController(node);
		}
		return ins;
	}

	private TestController(String node) {
		super(node);
	}

	@doCall(paths = "test1")
	public APIResponse test1(APIRequest req) throws Exception {
		System.out.println("test1");
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		return APIResponse.getNewSuccessResp("success1");
	}

	@doGet(paths = "test2")
	public APIResponse test2(APIRequest req) throws Exception {
		System.out.println("test2");
		JSONObject c = Param.getReqContent(req);
		Long appId = Param.getLong(c, "appId");
		Long userId = Param.getLong(c, "userId");

		return APIResponse.getNewSuccessResp("success2");
	}

}
