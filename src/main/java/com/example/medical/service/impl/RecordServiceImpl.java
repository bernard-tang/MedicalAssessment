package com.example.medical.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.medical.service.RecordService;
import com.example.medical.respository.RecordRepository;
import com.example.medical.entity.Record;

@Service
public class RecordServiceImpl implements RecordService {
	
	@Autowired
	RecordRepository recordRepository;
	
	@Override
	public List<Record> getAllRecords() {
		return recordRepository.findAll();
	}
	
	@Override
	public Record getRecordById(long id) {
		// get record by id
		return recordRepository.findById(id);
	}

	@Override
	public Record createRecord(Record record) {
		// create new record
		return recordRepository.save(record);
	}

	@Override
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
	public void deleteRecord(long id) {
		// delete record by id
		recordRepository.deleteById(id);
	}
	
}