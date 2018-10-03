package xhj.cn.start.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GetDomain {
	
	public List<Object> getDomain(Class<?> clazz) throws Exception{
		//存储对象对应变量名
		List<String> key = new ArrayList<String>();
		//存储对象对应变量值
		List<Object> vals = new ArrayList<Object>();
		//存储最终返回变量名与变量值数组
		List<Object> all = new ArrayList<Object>();
		//获取对象变量
		Field[] fields = clazz.getDeclaredFields();
		//循环判断并获取对象变量名与变量值
		for(Field f : fields) {
			if(f.get(clazz) != null && !f.get(clazz).equals("")) {
				key.add(f.getName());
				vals.add(f.get(clazz));
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
