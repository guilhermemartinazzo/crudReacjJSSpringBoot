package com.crudreactspringboot.crudreact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudreactspringboot.crudreact.entity.Entry;
import com.crudreactspringboot.crudreact.enumeration.EntryType;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

	public List<Entry> findByUserIdAndType(Long userId, EntryType type);
	
	public List<Entry> findByUserId(Long userId);
	
	public List<Entry> findByType(EntryType type);
}
