package com.crudreactspringboot.crudreact.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crudreactspringboot.crudreact.entity.Entry;
import com.crudreactspringboot.crudreact.entity.User;
import com.crudreactspringboot.crudreact.enumeration.EntryStatus;
import com.crudreactspringboot.crudreact.enumeration.EntryType;
import com.crudreactspringboot.crudreact.exception.BusinessException;
import com.crudreactspringboot.crudreact.repository.EntryRepository;
import com.crudreactspringboot.crudreact.util.ObjectMapperUtils;
import com.crudreactspringboot.crudreact.validator.EntryValidator;
import com.crudreactspringboot.crudreact.vo.EntryVO;

@Service
public class EntryService {

	@Autowired
	private EntryRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private EntryValidator entryValidator;

	@Transactional
	public Entry createEntry(EntryVO entryVO) {
		entryValidator.validateEntry(entryVO);
		Entry entryEntity = ObjectMapperUtils.getInstance().convertValue(entryVO, Entry.class);
		entryEntity.setStatus(EntryStatus.PENDING);
		setUser(entryVO, entryEntity);
		return repository.save(entryEntity);
	}

	private void setUser(EntryVO entryVO, Entry entryEntity) {
		User user = userService.findById(entryVO.getUser()).orElse(null);
		entryEntity.setUser(user);
	}

	public List<Entry> findByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	public List<Entry> findAll() {
		return repository.findAll();
	}

	public Optional<Entry> findById(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public void deleteById(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new BusinessException("Entry not found!");
		}
	}

	@Transactional
	public List<Entry> findByFilter(EntryVO vo) {
		Entry entryEntity = ObjectMapperUtils.getInstance().convertValue(vo, Entry.class);
		Example<Entry> example = Example.of(entryEntity,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Transactional
	public Entry updateEntry(Long id, EntryVO entryVO) {
		entryValidator.validateEntry(entryVO);
		return findById(id).map(entry -> {
			Entry entryEntity = buildEntryEntityFromVO(id, entryVO);
			BeanUtils.copyProperties(entryEntity, entry);
			repository.save(entry);
			return entry;
		}).orElseThrow(() -> new BusinessException("Entry not found!"));
	}
	
	@Transactional
	public Entry updateEntryStatus(Long id, EntryStatus status) {
		return findById(id).map(entry -> {
			entry.setStatus(status);
			repository.save(entry);
			return entry;
		}).orElseThrow(() -> new BusinessException("Entry not found!"));
	}

	@Transactional
	public BigDecimal getBalanceFromUser(Long id) {
		entryValidator.userInformedExists(id);
		BigDecimal expenses = repository.getBalanceByUserAndType(id, EntryType.EXPENSE);
		BigDecimal incomes = repository.getBalanceByUserAndType(id, EntryType.INCOME);
		incomes = incomes != null ? incomes : BigDecimal.ZERO;
		expenses = expenses != null ? expenses : BigDecimal.ZERO;
		return incomes.subtract(expenses);
	}

	private Entry buildEntryEntityFromVO(Long id, EntryVO entryVO) {
		Entry entryEntity = ObjectMapperUtils.getInstance().convertValue(entryVO, Entry.class);
		entryEntity.setId(id);
		setUser(entryVO, entryEntity);
		return entryEntity;
	}
}
