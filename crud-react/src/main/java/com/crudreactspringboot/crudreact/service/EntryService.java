package com.crudreactspringboot.crudreact.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.crudreactspringboot.crudreact.entity.Entry;
import com.crudreactspringboot.crudreact.enumeration.EntryStatus;
import com.crudreactspringboot.crudreact.enumeration.EntryType;
import com.crudreactspringboot.crudreact.repository.EntryRepository;
import com.crudreactspringboot.crudreact.util.ObjectMapperUtils;
import com.crudreactspringboot.crudreact.vo.EntryVO;

@Service
public class EntryService {

	@Autowired
	private EntryRepository repository;

	public Entry createEntry(EntryVO entryVO) {
		Entry entryEntity = ObjectMapperUtils.getInstance().convertValue(entryVO, Entry.class);
		entryEntity.setStatus(EntryStatus.PENDING);
		return repository.save(entryEntity);
	}

	public List<Entry> findAll() {
		return repository.findAll();
	}

	public Optional<Entry> findById(Long id) {
		return repository.findById(id);
	}

	public List<Entry> findByFilter(EntryVO vo) {
		Entry entryEntity = ObjectMapperUtils.getInstance().convertValue(vo, Entry.class);
		Example<Entry> example = Example.of(entryEntity,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	public List<Entry> findByUserIdAndType(Long userId, EntryType type) {
		if (null != userId && null != type) {
			return repository.findByUserIdAndType(userId, type);
		}
		if (userId != null) {
			return repository.findByUserId(userId);
		}
		if (type != null) {
			return repository.findByType(type);
		}
		return findAll();
	}
}
