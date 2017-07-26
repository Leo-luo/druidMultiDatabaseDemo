package com.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanUtil {
	
	private static Log logger = LogFactory.getLog(BeanUtil.class);


    /**
	 * 
	 * @Description: TODO 对象转换（set/get方法、参数一致的会被转换）
	 * @param target 目标对象class
	 * @param baseTO 被转换对象
	 * @throws
	 * @author cyh
	 * @date 2016年7月30日
	 */
	public static <T> T changeEntity(Class<T> target, Object baseTO) {
		T obj = null;
		if(baseTO == null)
			return null;
		try {
			obj = target.newInstance();
			List<Method> targetmethods = Arrays.asList(target.getDeclaredMethods());
			List<String> methodnames = new ArrayList<String>();
			for (Method method : targetmethods)
				methodnames.add(method.getName());
			Method[] methods = baseTO.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.getName().startsWith("get")) {
					String _tar = "set" + method.getName().substring(method.getName().indexOf("get") + 3);
					if (methodnames.contains(_tar)) {
						Method tarMethod = targetmethods.get(methodnames.indexOf(_tar));
						if(tarMethod.getParameterTypes()[0].equals(method.getReturnType())){
//							logger.info("methodName:" + method.getName() + "\t" + "value:" + method.invoke(baseTO));
							tarMethod.invoke(obj, method.invoke(baseTO));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("解析错误", e);
		}
		return obj;
	}
}
