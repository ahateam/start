package xhj.cn.start;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.topoints.core.controller.UserController;
import cn.topoints.utils.CodecUtils;
import cn.topoints.utils.api.http.Controller;
import cn.topoints.utils.data.DataSourceUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import xhj.cn.start.controller.TestController;

public class MainVerticle extends AbstractVerticle {

	protected Map<String, Controller> ctrlMap;

	public void init() {

		DataSourceUtils.initDataSourceConfig();

		ctrlMap = new HashMap<>();

		putCtrlInMap(ctrlMap, TestController.getInstance("test"));
		putCtrlInMap(ctrlMap, UserController.getInstance("user"));
	}

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new MainVerticle());
	}

	public void start() {
		System.out.println("started");

		init();

		HttpServer httpServer = vertx.createHttpServer();

		Set<String> allowHeaders = new HashSet<>();
		allowHeaders.add("x-requested-with");
		allowHeaders.add("Access-Control-Allow-Origin");
		allowHeaders.add("origin");
		allowHeaders.add("Content-Type");
		allowHeaders.add("accept");

		// REST 增删查改方法
		Set<HttpMethod> allowMethods = new HashSet<>();
		allowMethods.add(HttpMethod.PUT);
		allowMethods.add(HttpMethod.DELETE);
		allowMethods.add(HttpMethod.GET);
		allowMethods.add(HttpMethod.POST);
		allowMethods.add(HttpMethod.PATCH);

		// 实例化一个路由器出来，用来路由不同的rest接口
		Router router = Router.router(vertx);
		// 增加一个处理器，将请求的上下文信息，放到RoutingContext中
		router.route().handler(BodyHandler.create());

		// 处理一个post方法的rest接口
		router.post("/start/*").handler(this::handlePost);
		// 处理一个get方法的rest接口
		router.get("/start/*").handler(this::handleGet);

		httpServer.requestHandler(router::accept);
		httpServer.listen(8080, res -> {
			if (res.succeeded()) {
				System.out.println("Server is now listening!");
			} else {
				System.out.println("Fatal error: " + res.cause());
				vertx.close(); // 严重错误，不应该继续运行，需要关闭vertx实例
				System.exit(-1); // 自定义程序非正常退出码，这里定义255
			}
		});
	}

	private void handlePost(RoutingContext context) {
		HttpServerRequest req = context.request();
		HttpServerResponse resp = context.response();

		resp.putHeader("content-type", "application/json;charset=UTF-8");
		resp.putHeader("Access-Control-Allow-Origin", "*");
		exec(context, req, resp);
		resp.end();
	}

	// 逻辑同post方法
	private void handleGet(RoutingContext context) {

		HttpServerRequest req = context.request();
		HttpServerResponse resp = context.response();

		resp.putHeader("content-type", "application/json;charset=UTF-8");
		resp.putHeader("Access-Control-Allow-Origin", "*");// 设置跨域，目前不限制。TODO，将来需要设定指定的来源
		exec(context, req, resp);
		resp.end();
	}

	@SuppressWarnings("unused")
	private void putCtrlInMap(Map<String, Controller> map, Controller ctrl) {
		map.put(ctrl.getNode(), ctrl);
	}

	private static void writeThings(HttpServerResponse resp, String content) {
		resp.setStatusCode(200);
		int len = content.getBytes(CodecUtils.CHARSET_UTF8).length;
		resp.putHeader("Content-Length", Integer.toString(len));
		resp.write(content, CodecUtils.ENCODING_UTF8);
	}

	private void exec(RoutingContext context, HttpServerRequest req, HttpServerResponse resp) {
		String requestURI = req.path();

		System.out.println(req.method());
		System.out.println(req.path());

		String[] nodes = uri2Nodes(requestURI);

		if (null != nodes && nodes.length > 1) {
			String node = nodes[1];
			if (node.equals("list")) {
				// list被用于展示接口
				writeThings(resp, JSON.toJSONString(ctrlMap, true));
			} else {
				Controller ctrl = ctrlMap.get(node);
				if (null != ctrl) {
					try {
						ctrl.exec(nodes, context, req, resp);
					} catch (Exception e) {
						writeThings(resp, e.getMessage());
					}
				} else {
					// 返回404错误
					resp.setStatusCode(404);
					resp.setStatusMessage("missing controller");
				}
			}
		} else {
			// 返回404错误
			resp.setStatusCode(404);
			resp.setStatusMessage("missing controller");
		}
	}

	/**
	 * 斜杠
	 */
	private static final char URI_SLASH = '/';

	private static String[] uri2Nodes(String uri) {
		// 去前后空白
		String tmp = StringUtils.trim(uri);
		// 去前后斜杠
		if (tmp.length() > 0) {
			if (tmp.charAt(0) == URI_SLASH) {
				tmp = tmp.substring(1);
			}
		}
		if (tmp.length() > 0) {
			if (tmp.charAt(tmp.length() - 1) == URI_SLASH) {
				tmp = tmp.substring(0, tmp.length() - 1);
			}
		}

		// 根据斜杠拆分
		if (tmp.length() > 0) {
			String[] nodes = StringUtils.split(tmp, URI_SLASH);
			return nodes;
		} else {
			return null;
		}
	}
}
