package com.example.medical.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.medical.service.RecordService;
import com.example.medical.entity.Record;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component(value = "patientBean")
@ViewScoped
public class PatientController {

	private final RecordService recordService;
	
	private Record record;
	private Long recordId;
	private List<Record> records;

    // Constructor Injection for RecordService
    @Autowired
    public PatientController(RecordService recordService) {
        this.recordService = recordService;
    }
    
 // Automatically load all records when the bean is initialized
    @PostConstruct
    public void init() {
    	record = new Record(); 
        loadAllRecords();
    }
    
    
   public List<Record> loadAllRecords() {
	   return records = recordService.getAllRecords();
   }
   
   public void reset() {
	   record = new Record();
   }
   
   public String createRecord() {
       recordService.createRecord(record);
       loadAllRecords();
       return "viewRecords?faces-redirect=true";
   }

   public String updateRecord(Long id) {
       recordService.updateRecord(recordId, record);
       return "updateRecord?faces-redirect=true&recordId=" + id;
   }

   public String deleteRecord(Long id) {
       recordService.deleteRecord(id);
       records = recordService.getAllRecords();
       return "records?faces-redirect=true";
   }
   
   // Method to redirect to the edit page and pass the recordId
   public String editRecord(Long id) {
       this.recordId = id;  // Set the recordId
//       loadRecordById();
       return "updateRecord?faces-redirect=true&recordId=" + id;
   }

   public void loadRecordById() {
       Optional<Record> recordOptional = recordService.getRecordById(recordId);
       if (recordOptional.isPresent()) {
           record = recordOptional.get();
       }
   }
   
   
   @PostConstruct
   public void loadRecordFromQueryParam() {
       // Get the recordId from the URL query string
       HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       String recordIdParam = request.getParameter("recordId");
       if (recordIdParam != null) {
           try {
               recordId = Long.parseLong(recordIdParam);
               loadRecordById();
           } catch (NumberFormatException e) {
               // Handle invalid record ID
               System.out.println("Invalid record ID format");
           }
       }
   }
   
}
