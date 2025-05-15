package com.example.medical.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.medical.service.RecordService;
import com.example.medical.respository.RecordRepository;
import com.example.medical.entity.Record;

@Service
public class RecordServiceImpl implements RecordService {
	
	private static final Logger log = LoggerFactory.getLogger(RecordServiceImpl.class);
			
	private final RecordRepository recordRepository;
	
	// Constructor injection of RecordRepository
    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }
    
	@Override
	public List<Record> getAllRecords() {
		return recordRepository.findAll();
	}
	
	@Override
	public Optional<Record> getRecordById(long id) {
		// get record by id
		return recordRepository.findById(id);
	}

	@Override
	@Transactional
	public Record createRecord(Record record) {
		// create new record
		return recordRepository.save(record);
	}

	@Override
	@Transactional
	public Record updateRecord(Long id, Record updatedRecord) {
		// update record if id is found
		
        return recordRepository.findById(id).map(record -> {
            record.setName(updatedRecord.getName());
            record.setAge(updatedRecord.getAge());
            record.setMedicalHistory(updatedRecord.getMedicalHistory());
            return recordRepository.save(record);
        }).orElseThrow(() -> new RuntimeException("Record not found"));
    }

	@Override
	@Transactional
	public void deleteRecord(long id) {
		// delete record by id
		recordRepository.deleteById(id);
	}
	
}