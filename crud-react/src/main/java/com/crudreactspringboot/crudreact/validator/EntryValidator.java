package com.crudreactspringboot.crudreact.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crudreactspringboot.crudreact.exception.BusinessException;
import com.crudreactspringboot.crudreact.service.UserService;
import com.crudreactspringboot.crudreact.vo.EntryVO;

@Component
public class EntryValidator {

	@Autowired
	private UserService userService;

	public void validateEntry(EntryVO vo) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("Description", vo.getDescription());
		attributes.put("EntryMonth", vo.getEntryMonth());
		attributes.put("EntryValue", vo.getEntryValue());
		attributes.put("EntryYear", vo.getEntryYear());
		attributes.put("Status", vo.getStatus());
		attributes.put("Type", vo.getType());
		attributes.put("User", vo.getUser());

		Set<Entry<String, Object>> entrySet = attributes.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			if (null == entry.getValue()) {
				throw new BusinessException(entry.getKey() + " must be informed!");
			}
		}
		userInformedExists(vo.getUser());
	}

	public void userInformedExists(Long idUser) {
		if (!userService.existsById(idUser)) {
			throw new BusinessException("User Not Found!");
		}
	}
}
