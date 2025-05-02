package com.example.medical.controller;

import com.example.medical.entity.Record;
import com.example.medical.service.RecordService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/records")
@CrossOrigin(origins = "*")
public class RecordController {

	private static final Logger log = LoggerFactory.getLogger(RecordController.class);
	
	@Autowired
    private RecordService recordService;

    @GetMapping("/")
    public ResponseEntity<List<Record>> getAll() {
    	List<Record> results = recordService.getAllRecords();
    	
    	return new ResponseEntity<List<Record>>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Record> getById(@PathVariable Long id) {
        return new ResponseEntity<Record>(recordService.getRecordById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Record> create(@RequestBody Record record) {
        
        return new ResponseEntity<Record>(recordService.createRecord(record), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Record> update(@PathVariable Long id, @RequestBody Record record) {
        return new ResponseEntity<Record>(recordService.updateRecord(id, record), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Record> delete(@PathVariable Long id) {
    	recordService.deleteRecord(id);
    	return new ResponseEntity<Record>(new Record(), HttpStatus.OK);
    }
}