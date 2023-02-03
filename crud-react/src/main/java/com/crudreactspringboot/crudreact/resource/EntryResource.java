package com.crudreactspringboot.crudreact.resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crudreactspringboot.crudreact.entity.Entry;
import com.crudreactspringboot.crudreact.enumeration.EntryStatus;
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
	public ResponseEntity<List<Entry>> findByUserIdAndType(@RequestParam(required = false) Long userId,
			@RequestParam(required = false) EntryType type) {
		return ResponseEntity.ok(service.findByUserIdAndType(userId, type));
	}

	@GetMapping("/filter2")
	public ResponseEntity<List<Entry>> findByFilter(@RequestParam(required = false) Long id,
			@RequestParam(required = false) EntryType type, @RequestParam(required = false) EntryStatus status,
			@RequestParam(required = false) String description, @RequestParam(required = false) Integer entryYear,
			@RequestParam(required = false) Integer entryMonth, @RequestParam(required = false) BigDecimal entryValue,
			 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateRegistration, @RequestParam(required = false) Long userId) {
		EntryVO entryVO = EntryVO.builder()
				.id(id)
				.description(description)
				.entryMonth(entryMonth)
				.entryYear(entryYear)
				.entryValue(entryValue)
				.user(userId)
				.dateRegistration(dateRegistration)
				.status(status).type(type).build();
		return ResponseEntity.ok(service.findByFilter(entryVO));
	}

	@PostMapping
	public ResponseEntity<Entry> creatEntry(@RequestBody EntryVO entry) {
		return ResponseEntity.ok(service.createEntry(entry));
	}
}
