package xhj.cn.start.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.topoints.utils.data.rds.RDSUtils;

public class GetDomain {
	
	public List<Object> getDomain(Object obj) throws Exception{
		//存储对象对应变量名
		List<String> key = new ArrayList<String>();
		//存储对象对应变量值
		List<Object> vals = new ArrayList<Object>();
		//存储最终返回变量名与变量值数组
		List<Object> all = new ArrayList<Object>();
		//获取对象变量
		Field[] fields = obj.getClass().getDeclaredFields();
		//循环判断并获取对象变量名与变量值
		for(Field f : fields) {
			if(f.getGenericType().toString().equals("class java.lang.String")) {
				if(f.get(obj) != null && !f.get(obj).equals("")) {
					key.add(RDSUtils.underscoreName(f.getName()));
					vals.add((String)f.get(obj));
				}
			}else if(f.getGenericType().toString().equals("class java.lang.Long")) {
				if(f.get(obj) != null) {
					key.add(RDSUtils.underscoreName(f.getName()));
					vals.add((Long)f.get(obj));
				}
			}else if(f.getGenericType().toString().equals("class java.lang.Integer")) {
				if(f.get(obj) != null) {
					key.add(RDSUtils.underscoreName(f.getName()));
					vals.add((Integer)f.get(obj));
				}
			}else if(f.getGenericType().toString().equals("class java.lang.Double")) {
				if(f.get(obj) != null) {
					key.add(RDSUtils.underscoreName(f.getName()));
					vals.add((Double)f.get(obj));
				}
			}else if(f.getGenericType().toString().equals("class java.util.Date")) {
				if(f.get(obj) != null) {
					key.add(RDSUtils.underscoreName(f.getName()));
					vals.add((Date)f.get(obj));
				}
			}else if(f.getGenericType().toString().equals("class java.util.Byte")) {
				if(f.get(obj) != null) {
					key.add(RDSUtils.underscoreName(f.getName()));
					vals.add((Byte)f.get(obj));
				}
			}
		}
		//变量名集合转换数组
		String[] keys = new String[key.size()];
		key.toArray(keys);
		//变量值集合转换数组
		Object[] values = new Object[vals.size()];
		vals.toArray(values);
		all.add(keys);
		all.add(values);
		return all;
	}

}
