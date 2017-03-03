package com.cybernetica.bj.common.interfaces;

import java.util.HashMap;
import java.util.Map;

public interface Singleton<T> {

	final static Map<Class<?>, Object> singletons = new HashMap<>();// not  thread  safe but  fast

	@SuppressWarnings("unchecked")
	static <T> T getSingleton(Class<T> cls) {

		if (singletons.containsKey(cls))
			return (T) singletons.get(cls);
		T instance;
		Class<T> instanceClass=cls;
		if (cls.isInterface()) {
			// get class
			instanceClass=null;
			
			//try under impl package
			try {
				int length = cls.getSimpleName().length();
				String part = cls.getName().substring(0, cls.getName().length()-length);
				instanceClass = (Class<T>) Class.forName(part+"impl."+cls.getSimpleName()+"Impl");
			} catch (ClassNotFoundException e) {				
			}
			
			if(instanceClass==null) {//try simply by appending impl
				try {
					instanceClass = (Class<T>) Class.forName(cls.getName() + "Impl");
				} catch (ClassNotFoundException e) {				
				}				
			}
			
			if(instanceClass==null) {
				throw new RuntimeException("not found " + cls.getName());
			}
		}

		try {
			instance = instanceClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		singletons.put(cls, instance);
		return instance;
	}

}
