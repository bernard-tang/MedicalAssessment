package com.example.medical.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.medical.entity.Record;


@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    // Method to find a record by name
	Record findByName(String name);
	
	Optional<Record> findById(long id);

    // Other custom queries can be defined here if needed
}
