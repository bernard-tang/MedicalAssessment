package com.example.medical.controllerTest;

import com.example.medical.entity.Record;
import com.example.medical.respository.RecordRepository;
import com.example.medical.service.RecordService;
import com.example.medical.service.impl.RecordServiceImpl;
import com.example.medical.controller.RecordController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class RecordControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecordRepository recordRepository;  // Mock repository
    
    @InjectMocks
    private RecordServiceImpl recordService;

    @InjectMocks
    private RecordController recordController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        recordController = new RecordController(recordService);
        recordService = new RecordServiceImpl(recordRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(recordController).build();
    }

    @Test
    public void testCreateRecord() throws Exception {
        Record record = new Record("Jane Doe", 25, Arrays.asList("Asthma", "Allergy"));
        Mockito.when(recordService.createRecord(Mockito.any(Record.class))).thenReturn(record);

        mockMvc.perform(post("/api/records")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Jane Doe")));
    }

    @Test
    public void testGetAllRecords() throws Exception {
    	
    	// Mock records to return
        List<Record> records = Arrays.asList(
            new Record(1L, "John Doe", 30, Arrays.asList("Diabetes", "Hypertension")),
            new Record(2L, "Jane Doe", 25, Arrays.asList("Asthma", "Cold"))
        );

        // Mock the service to return a non-empty list of records
        Mockito.when(recordService.getAllRecords()).thenReturn(records);
        
     // Perform the GET request to fetch all records
        mockMvc.perform(get("/api/records"))
                .andExpect(status().isOk())  // Expect 200 OK
                .andExpect(jsonPath("$.size()", is(2)))  // Ensure the size of the returned collection is 2
                .andExpect(jsonPath("$[0].id", is(1)))  // Check the first record's ID
                .andExpect(jsonPath("$[0].name", is("John Doe")))  // Check the first record's name
                .andExpect(jsonPath("$[1].id", is(2)))  // Check the second record's ID
                .andExpect(jsonPath("$[1].name", is("Jane Doe")));  // Check the second record's name
    }

    @Test
    public void testGetRecordById() throws Exception {
        Record record = new Record(1L, "John Doe", 30, Arrays.asList("Diabetes", "Hypertension"));
        Mockito.when(recordRepository.findById(1L)).thenReturn(Optional.of(record));

        mockMvc.perform(get("/api/records/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void testDeleteRecord() throws Exception {
        Mockito.doNothing().when(recordRepository).deleteById(1L);

        mockMvc.perform(delete("/api/records/1"))
                .andExpect(status().isNoContent());
    }
    
}
