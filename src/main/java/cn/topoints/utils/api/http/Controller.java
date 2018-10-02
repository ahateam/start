package cn.topoints.utils.api.http;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.topoints.utils.CodecUtils;
import cn.topoints.utils.api.BaseRC;
import cn.topoints.utils.api.RC;
import cn.topoints.utils.api.ServerException;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public abstract class Controller {

	private static Logger log = LoggerFactory.getLogger(Controller.class);

	/**
	 * API方法
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	protected @interface doCall {
		public String[] paths();

		// 默认所有的方法都需要验证，只有标记false的不验证，如登录，注册等方法
		public boolean verify() default true;
	}

	/**
	 * Controller的url根目录节点
	 */
	protected String node;

	protected Map<String, Object[]> getMethods = new HashMap<String, Object[]>();

	protected Map<String, Object[]> postMethods = new HashMap<String, Object[]>();

	protected Map<String, Object[]> callMethods = new HashMap<String, Object[]>();

	/**
	 * 无效，并被警告的方法
	 */
	protected Map<String, Method> warningMethods = new HashMap<String, Method>();

	protected Controller(String node) {
		this.node = node;

		Method[] ms = this.getClass().getMethods();
		for (Method m : ms) {
			// 识别CALL
			{
				doCall callMethod = m.getAnnotation(doCall.class);
				if (null != callMethod) {
					String[] paths = callMethod.paths();
					if (null == paths || paths.length < 1) {
						log.info(">>>Method add nothing");
					} else {
						for (String path : paths) {
							if (callMethods.containsKey(path)) {
								// 如果要添加的path，已经被暂用，则将此方法列入警告
								warningMethods.put(path, m);
							} else {
								callMethods.put(path, new Object[] { m, callMethod.verify() });
							}
						}
					}
					continue;
				}
			}

		}
	}

	/**
	 * 获取当前Controller的url节点字符串
	 */
	public String getNode() {
		return node;
	}

	/**
	 * 执行方法，供Servlet入口进行调用
	 */
	public void exec(String[] nodes, RoutingContext context, HttpServerRequest req, HttpServerResponse resp)
			throws IOException {

		if (null != nodes && nodes.length > 2) {
			// 有方法节点
			String node = nodes[2];

			// 先处理CALL
			Object[] ms = callMethods.get(node);
			if (null != ms) {
				Method m = (Method) ms[0];
				boolean verify = (boolean) ms[1];
				execCall(m, verify, context, req, resp);
				// 已处理，直接返回
				return;
			}
		} else {
			// 返回404错误
			resp.setStatusCode(404);
			resp.setStatusMessage("missing controller method");
			return;
		}

	}

	private void execCall(Method m, boolean verify, RoutingContext context, HttpServerRequest req,
			HttpServerResponse resp) throws IOException {
		try {
			HttpMethod reqMethod = req.method();
			String strRequest = null;
			if (reqMethod.equals(HttpMethod.GET)) {
				// GET方法的内容在url里
				// 获取GET方法的req参数
				// 无需进行urlDecode
				strRequest = req.getParam("req");
			} else {
				// 其它方法都按POST处理，认为内容在body里
				strRequest = context.getBodyAsString(CodecUtils.ENCODING_UTF8);
				System.out.println(strRequest);
			}

			if (StringUtils.isNotBlank(strRequest)) {
				APIRequest jsonRequest = JSON.parseObject(strRequest, APIRequest.class);
				if (verify) {
					String id = jsonRequest.id;
					String v = jsonRequest.v;

					// getsession，使用token验证，然后跟v对比

					// 暂时不验证，直接通过

					APIResponse jsonResponse = (APIResponse) m.invoke(this, jsonRequest);
					doResponseSuccess(resp, jsonResponse);
				} else {
					APIResponse jsonResponse = (APIResponse) m.invoke(this, jsonRequest);
					doResponseSuccess(resp, jsonResponse);
				}
			} else {
				doResponseFailure(resp, BaseRC.EMPTY_REQUEST, "");
			}

			// 如果调用出现异常，则按服务端规范，将错误信息按APIResponse格式进行返回
		} catch (InvocationTargetException ite) {
			// 反射的代理异常，剥壳后才是真实的异常
			Throwable targetException = ite.getTargetException();
			dealException(resp, targetException);
		} catch (Exception e) {
			dealException(resp, e);
		}
	}

	private void dealException(HttpServerResponse resp, Throwable e) throws IOException {
		if (e instanceof ServerException) {
			ServerException serverException = (ServerException) e;
			// 获取错误码并按APIResponse格式返回
			doResponseFailure(resp, serverException.getRC(), serverException.getMessage());
		} else {
			// 其它异常，则生成默认的response
			String content = e.getMessage();
			if (StringUtils.isBlank(content)) {
				doResponseFailure(resp, BaseRC.FAILURE, e.toString());
			} else {
				doResponseFailure(resp, BaseRC.FAILURE, content);
			}
		}
	}

	private void execGet(Method m, HttpServerRequest req, HttpServerResponse resp) throws IOException {
		try {
			m.invoke(this, req, resp);
			// 如果调用出现异常，则按HTTP规范直接返回异常信息
		} catch (InvocationTargetException ite) {
			// 反射的代理异常，剥壳后才是真实的异常
			String targetException = ite.getTargetException().getMessage();
			resp.setStatusCode(500);
			resp.setStatusMessage(targetException);
		} catch (Exception e) {
			String targetException = e.getMessage();
			resp.setStatusCode(500);
			resp.setStatusMessage(targetException);
		}
	}

	private void execPost(Method m, HttpServerRequest req, HttpServerResponse resp) throws IOException {
		try {
			m.invoke(this, req, resp);
			// 如果调用出现异常，则按HTTP规范直接返回异常信息
		} catch (InvocationTargetException ite) {
			// 反射的代理异常，剥壳后才是真实的异常
			String targetException = ite.getTargetException().getMessage();
			log.debug("execPost299>>>{}", targetException);
			resp.setStatusCode(500);
			resp.setStatusMessage(targetException);
		} catch (Exception e) {
			String targetException = e.getMessage();
			resp.setStatusCode(500);
			resp.setStatusMessage(targetException);
		}
	}

	private static void doResponseSuccess(HttpServerResponse resp, APIResponse response) throws IOException {
		resp.setStatusCode(200);
		writeThings(resp, JSON.toJSONString(response));
	}

	private static void doResponseFailure(HttpServerResponse resp, RC rc, String content) throws IOException {
		resp.setStatusCode(200);
		writeThings(resp, JSON.toJSONString(APIResponse.getNewFailureResp(rc, content)));
	}

	private static void writeThings(HttpServerResponse resp, String content) {
		resp.setStatusCode(200);
		int len = content.getBytes(CodecUtils.CHARSET_UTF8).length;
		resp.putHeader("Content-Length", Integer.toString(len));
		resp.write(content, CodecUtils.ENCODING_UTF8);
	}
}
