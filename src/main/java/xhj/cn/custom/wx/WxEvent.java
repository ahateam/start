package xhj.cn.custom.wx;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.topoints.utils.data.DataSource;
import xhj.cn.custom.util.HttpClientUtil;

public class WxEvent {
	
	private static Logger log = LoggerFactory.getLogger(WxEvent.class);
	
	private WxEventService wxEventService;
	private DataSource dsRds;
	
	/*
	 * 二维码类型
	 */
	private static final String QR_SCENE = "QR_SCENE";   //临时整形参数值
	private static final String QR_STR_SCENE = "QR_STR_SCENE";   //临时字符串参数值
	private static final String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";   //永久整形参数值
	private static final String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";   //永久字符串参数值
	
	/**
	 * @描述 获取AccessToken
	 * @param appid
	 * @param appsecret
	 * @return
	 * @throws Exception
	 */
	public static String getAccessToken(String appid, String appsecret) throws Exception {  
	    String result = HttpClientUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret);
	    JSONObject jsonObject = JSONObject.parseObject(result);
	    if (null != jsonObject) {
	        try {  
	            result = jsonObject.getString("access_token");  
	        } catch (JSONException e) {  
	        	log.info("获取token失败 errcode:"+jsonObject.getInteger("errcode") +",errmsg:"+ jsonObject.getString("errmsg"));                
	        }  
	    }  
	    return result;  
	}
	
	
	/**
	 * @描述 获取临时二维码ticket
	 * @param accessToken
	 * @param expireSeconds
	 *                   二维码有效时间(最大不超过2592000（即30天）)
	 * @param sceneId
	 *                   场景ID
	 * @return
	 * @throws Exception 
	 */
	public String createTempTicket(String accessToken, Integer expireSeconds, String scene_str) throws Exception {
	    Map<String,String> strMap = new HashMap<String,String>();  
	    strMap.put("scene_str",scene_str);  
	    Map<String,Map<String,String>> mapMap = new HashMap<String,Map<String,String>>();  
	    mapMap.put("scene", strMap);  
	    //定义所需传递的参数
	    Map<String,Object> paramsMap = new HashMap<String,Object>();  
	    paramsMap.put("expire_seconds", expireSeconds);  
	    paramsMap.put("action_name", QR_SCENE);  
	    paramsMap.put("action_info", mapMap);  
	    //Map转换json串
	    String data = String.format("data=%s", JSONObject.toJSONString(paramsMap));
	    //获取微信接口返回参数
	    String result = HttpClientUtil.post("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, data);
	    //json串转json对象
	    JSONObject jsonObject = JSONObject.parseObject(result);
	    //获取ticket参数值
	    return jsonObject.getString("ticket");
	    //直接获取生成二维码的url
	    //return jsonObject.getString("url");
	}
	
	/**
	 * @描述 获取永久二维码ticket
	 * @param accessToken
	 * @param sceneId
	 *              场景ID
	 * @return
	 * @throws Exception
	 */
	public String createForeverTicket(String accessToken, String scene_str) throws Exception {
		Map<String,String> strMap = new HashMap<String,String>();  
		strMap.put("scene_str",scene_str);  
	    Map<String,Map<String,String>> mapMap = new HashMap<String,Map<String,String>>();  
	    mapMap.put("scene", strMap);  
	    //定义所需传递的参数
	    Map<String,Object> paramsMap = new HashMap<String,Object>();  
	    paramsMap.put("action_name", QR_LIMIT_SCENE);  
	    paramsMap.put("action_info", mapMap);  
	    //Map转换json串
	    String data = String.format("data=%s", JSONObject.toJSONString(paramsMap));
	    //获取微信接口返回参数
	    String result = HttpClientUtil.post("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, data);
	    //json串转json对象
	    JSONObject jsonObject = JSONObject.parseObject(result);
	    //获取ticket参数值
	    return jsonObject.getString("ticket");
	}
	
	/**
	 * @描述 领取单张卡券二维码
	 * @param accessToken
	 * @param cardId
	 *             卡卷ID
	 * @param expire_seconds
	 *             卡卷有效时间（范围60 ~ 1800秒，不填默认为365天）
	 * @param outer_str
	 *             场景值
	 * @return
	 * @throws Exception
	 */
	public String cardTicket(String accessToken, String cardId, Integer expire_seconds, String outer_str) throws Exception {
		Map<String,String> card = new HashMap<String,String>();
		card.put("card_id", cardId);
		card.put("outer_str", outer_str);
		Map<String,Map<String,String>> mapMap = new HashMap<String,Map<String,String>>();  
	    mapMap.put("card", card);
	    //定义所需传递的参数
	    Map<String,Object> paramsMap = new HashMap<String,Object>();  
	    paramsMap.put("action_name", "QR_CARD");
	    //判断是否设置时间
	    if(expire_seconds != null) {
	    	paramsMap.put("expire_seconds", expire_seconds);
	    }
	    paramsMap.put("action_info", mapMap);
	    //Map转换json串
	    String data = String.format("data=%s", JSONObject.toJSONString(paramsMap));
	    //获取微信接口返回参数
	    String result = HttpClientUtil.post("https://api.weixin.qq.com/card/qrcode/create?access_token="+accessToken, data);
	    //json串转json对象
	    JSONObject jsonObject = JSONObject.parseObject(result);
	    //获取ticket参数值
	    return jsonObject.getString("ticket");
	}
	

}
