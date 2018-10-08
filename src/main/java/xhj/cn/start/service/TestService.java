package xhj.cn.start.service;

import java.util.List;

import org.junit.Test;

import xhj.cn.start.domain.Product;

public class TestService {
	
	public List<Product> getProductListByTags(String tagKey, Object[] tags,Integer count,Integer offset){
		StringBuffer sb = new StringBuffer();
		sb.append("WHERE ");
		sb.append("JSON_CONTAINS(tags->'$.").append(tagKey).append("', '");
		for (Object tag : tags) {
			sb.append("\"").append(tag).append("\",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("')");
		System.err.println(sb);
		return null;	
	}
	
	@Test
	public void testOn() {
		getProductListByTags( "key", new Object[] {123,"456"}, null, null);
	}

}
