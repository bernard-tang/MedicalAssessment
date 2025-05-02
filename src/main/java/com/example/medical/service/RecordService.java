package com.example.medical.service;

import java.util.List;

import com.example.medical.entity.Record;

public interface RecordService {
	List<Record> getAllRecords();
	Record getRecordById(long id);
	Record createRecord(Record record);
	Record updateRecord(Long id, Record updatedRecord);
	void deleteRecord(long id);
}