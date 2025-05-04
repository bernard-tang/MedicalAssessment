package com.example.medical.service;

import java.util.List;
import java.util.Optional;

import com.example.medical.entity.Record;

public interface RecordService {
	List<Record> getAllRecords();
	Optional<Record> getRecordById(long id);
	Record createRecord(Record record);
	Record updateRecord(Long id, Record updatedRecord);
	void deleteRecord(long id);
}