package xhj.cn.custom.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.topoints.utils.CodecUtils;
import cn.topoints.utils.api.http.APIResponse;
import cn.topoints.utils.api.http.Controller;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

public class WxEventController extends Controller {

	private static Logger log = LoggerFactory.getLogger(WxEventController.class);

	private static WxEventController ins;

	public static synchronized WxEventController getInstance(String node) {
		if (null == ins) {
			ins = new WxEventController(node);
		}
		return ins;
	}

	private WxMpService wxMpService;
	private WxMpInMemoryConfigStorage wxMpConfigStorage;
	private WxMpMessageRouter wxMpMessageRouter;

	private WxEventController(String node) {
		super(node);
		try {
			// 微信参数配置
			wxMpConfigStorage = new WxMpInMemoryConfigStorage();
			wxMpConfigStorage.setAppId("wx9aaf23b05328a771"); // APPid
			wxMpConfigStorage.setSecret("6b72b49c33db086d6f62931f94e9ee1b"); // AppSecret
			wxMpConfigStorage.setToken("wx3ch"); // 设置微信公众号的token
			wxMpConfigStorage.setAesKey("6tLn50b5o97PhgdiVb5Ek0780VLx6yG97eiKTE9waxZ"); // 设置微信公众号的EncodingAESKey
			wxMpService = new WxMpServiceImpl();
			wxMpService.setWxMpConfigStorage(wxMpConfigStorage);

			/**
			 * 关注事件
			 */
			WxMpMessageHandler handler = new WxMpMessageHandler() {
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
						WxMpService wxMpService, WxSessionManager sessionManager) {
					WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content("欢迎关注！！！")
							.fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
					return m;
				}
			};

			/**
			 * test消息回复
			 */
			WxMpMessageHandler testHander = new WxMpMessageHandler() {
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
						WxMpService wxMpService, WxSessionManager sessionManager) {
					WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content("test Go")
							.fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
					return m;
				}
			};

			// 路由器配置
			wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
			wxMpMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)
					.eventKey("").handler(handler).end()

					.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).content("test").handler(testHander).end();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	// 微信监听入口
	@GET(path = "monitorEvent")
	public void monitorEvent(HttpServerRequest req, HttpServerResponse resp, RoutingContext context) {
		System.err.println("testIn");

		String strRet = "failure";
		try {

			WxMenu wxMenu = new WxMenu();

			// 微信加密签名
			String signature = req.getParam("signature");
			// 时间戳
			String timestamp = req.getParam("timestamp");
			// 随机数
			String nonce = req.getParam("nonce");
			// 随机字符串
			String echostr = req.getParam("echostr");

			String encryptType = StringUtils.isBlank(req.getParam("encrypt_type")) ? "raw"
					: req.getParam("encrypt_type");

			if ("raw".equals(encryptType)) {
				System.err.println(context.getBodyAsString(CodecUtils.ENCODING_UTF8));
				// 明文传输的消息
				WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(context.getBodyAsString(CodecUtils.ENCODING_UTF8));
				System.err.println("inms:" + inMessage);
				WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
				System.err.println("outms:" + outMessage);
				if (outMessage == null) {
					// 为null，说明路由配置有问题，需要注意
					ret(resp, strRet);
				} else {
					ret(resp, outMessage.toXml());
				}
				return;
			}

			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (WxTokenController.checkSignature(signature, timestamp, nonce)) {
				strRet = echostr;
			} else {
				strRet = "failure";
			}
		} catch (Exception e) {
			log.error("微信监听事件异常：", e);
			strRet = "failure";
		}

		ret(resp, strRet);
	}

	// 回复编码
	private void ret(HttpServerResponse resp, String str) {
		int len = str.getBytes(CodecUtils.CHARSET_UTF8).length;
		resp.putHeader("content-type", "text/html;charset=utf-8");
		resp.putHeader("content-length", Integer.toString(len));
		resp.write(str);
	}

	/*
	 * 网页授权
	 */
	@GET(path = "wxTest1")
	public APIResponse test(HttpServerRequest req, HttpServerResponse resp) {
		System.err.println("testGo");
		String url = "";
		String url2 = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
		System.err.println(url2);
		return APIResponse.getNewSuccessResp(url2);
	}

	/*
	 * 根据用户反馈授权获取对应token
	 */
	@GET(path = "wxTest2")
	public APIResponse getAccessToken(HttpServerRequest req, HttpServerResponse resp) throws Exception {
		System.err.println("test2Go");
		String code = req.getParam("code");
		System.err.println(code);
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
		System.err.println(wxMpUser.getOpenId());
		return APIResponse.getNewSuccessResp("success");
	}

}
