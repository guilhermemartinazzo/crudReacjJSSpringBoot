package com.crudreactspringboot.crudreact.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class ObjectMapperUtils {

	private static ObjectMapper instance;

	private ObjectMapperUtils() {

	}

	public static ObjectMapper getInstance() {
		if (null == instance) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper;
		}
		return instance;
	}
}
