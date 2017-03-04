package com.cybernetica.bj.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static ObjectMapper mapper = new ObjectMapper();

	
	public static String toString(Object o) throws JsonProcessingException{
		return mapper.writeValueAsString(o);
	}
	
	public static <T> T fromString(String content,Class<T> cls) throws IOException{
		return mapper.readValue(content, cls);
	}
}
