package com.example.medical.serviceTest;

import com.example.medical.entity.Record;
import com.example.medical.respository.RecordRepository;
import com.example.medical.service.RecordService;
import com.example.medical.service.impl.RecordServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordServiceImpl recordService;
    
    @Test
    public void testCreateRecord() {
        Record newRecord = new Record(null, "John Doe", 30, Arrays.asList("Diabetes"));
        Record savedRecord = new Record(1L, "John Doe", 30, Arrays.asList("Diabetes"));

        Mockito.when(recordRepository.save(Mockito.any(Record.class))).thenReturn(savedRecord);

        Record result = recordService.createRecord(newRecord);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
    }
    
    @Test
    public void testUpdateRecord() {
        // Create an existing record and an updated record
    	Long id = 1L;
        Record existingRecord = new Record(id, "John Doe", 30, Arrays.asList("Diabetes"));
        Record updatedRecord = new Record(id, "John Doe", 31, Arrays.asList("Diabetes", "Hypertension"));

        // Mock the repository to return the existing record when findById is called
        Mockito.when(recordRepository.findById(id)).thenReturn(Optional.of(existingRecord));
//        Mockito.lenient().when(recordRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingRecord));


        // Mock the repository's save method to return the updated record when called
        Mockito.when(recordRepository.save(Mockito.any(Record.class))).thenReturn(updatedRecord);

        // Call the updateRecord method on the service
        Record result = recordService.updateRecord(id, updatedRecord);

        // Assert the result
        assertNotNull(result);
        assertEquals(1L, result.getId());  // Check the ID is the same
        assertEquals(31, result.getAge());  // Check the age was updated
        assertTrue(result.getMedicalHistory().contains("Hypertension"));  // Check the medical history was updated
    }
    
    @Test
    public void testGetRecordById() {
        Record record = new Record("John Doe", 30, Arrays.asList("Diabetes", "Hypertension"));
        Mockito.when(recordRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(record));

        Optional<Record> foundRecord = recordService.getRecordById(1L);

        assertTrue(foundRecord.isPresent());
        assertEquals("John Doe", foundRecord.get().getName());
    }
}
