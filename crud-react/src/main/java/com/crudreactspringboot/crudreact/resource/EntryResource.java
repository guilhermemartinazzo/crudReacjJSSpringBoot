package com.crudreactspringboot.crudreact.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crudreactspringboot.crudreact.entity.Entry;
import com.crudreactspringboot.crudreact.enumeration.EntryType;
import com.crudreactspringboot.crudreact.service.EntryService;
import com.crudreactspringboot.crudreact.vo.EntryVO;

@RestController
@RequestMapping("/entry")
public class EntryResource {

	
	@Autowired
	private EntryService service;
	
	@GetMapping
	public ResponseEntity<List<Entry>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Entry> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id).orElseThrow());
	}
	
	@GetMapping("/filter")
	public ResponseEntity<List<Entry>> findByUserId(@RequestParam(required = false) Long userId, @RequestParam(required = false) EntryType type) {
		return ResponseEntity.ok(service.findByUserIdAndType(userId, type));
	}
	
	@PostMapping
	public ResponseEntity<Entry> creatEntry(@RequestBody EntryVO entry) {
		return ResponseEntity.ok(service.createEntry(entry));
	}
}
