package com.crudreactspringboot.crudreact.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crudreactspringboot.crudreact.entity.Entry;
import com.crudreactspringboot.crudreact.enumeration.EntryType;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

	public List<Entry> findByUserIdAndType(Long userId, EntryType type);
	
	public List<Entry> findByUserId(Long userId);
	
	public List<Entry> findByType(EntryType type);
	
	@Query(value = "Select SUM(e.entryValue) FROM Entry e where e.user.id =:userId and e.type =:type group by e.user")
	public BigDecimal getBalanceByUserAndType(Long userId, EntryType type);
}
