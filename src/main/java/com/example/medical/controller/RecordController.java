package com.example.accessingdatajpa.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
//import org.springframework.data.repository.CrudRepository;

//import com.example.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.accessingdatajpa.entity.Product;
import com.example.accessingdatajpa.repository.ProductRepository;
import com.example.accessingdatajpa.util.payload.Bundle;
import com.example.accessingdatajpa.util.payload.Resource;
import com.example.accessingdatajpa.util.payload.ResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
//@RequestMapping("/customer")
public class ProductController {
	
//	@Autowired
//	UserService userService;
	
	@Autowired
	ProductRepository repository;
	
	 @Autowired
	 ResourceUtil resourceUtil;
	
	@PutMapping("/product")
	public ResponseEntity<Product> create(@RequestBody String payload) {
		Product result = null;
		try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(payload, Product.class);

            System.out.println(product); // Output the converted Java object
//            return null;
            result = repository.save(product);
            
            return new ResponseEntity<Product>(
            		result, 
                    HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return new ResponseEntity<Product>(
				result, 
                HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/getAllProducts")
	public ResponseEntity<Bundle> getAllProducts(@RequestParam("offset") Optional<Integer> offset, @RequestParam("limit") Optional<Integer> limit) {
		List<Product> results = new ArrayList<Product>();
		Bundle bundle = new Bundle();
		
		repository.findAll().forEach(results::add);
		
		if(offset.isPresent() && limit.isPresent())
		{
			int offsetValue = offset.get();
			int limitValue = limit.get();
			
			int fromIndex = offsetValue;
	        int toIndex = Math.min(offsetValue + limitValue, results.size());
	        
	        if(limitValue == -1) {
	        	bundle = resourceUtil.convertListObjectToBundle(results, results.size());
				
				return new ResponseEntity<>(
						bundle, 
		                HttpStatus.OK);
	        }
	        
			List<Product> filteredResults = results.subList(fromIndex, toIndex);
			
//			for(Object obj : filteredResults) {
//				bundle.getEntry().add(resourceUtil.convertObjectToResource(obj));
//			}
//			bundle.setTotal(results.size());
			
			bundle = resourceUtil.convertListObjectToBundle(filteredResults, results.size());
			
			return new ResponseEntity<>(
					bundle, 
	                HttpStatus.OK);
		}
		
		return new ResponseEntity<>(
				bundle, 
                HttpStatus.OK);
	}
	
	@GetMapping("/getProductsByCategory")
	public ResponseEntity<List<Product>> getProductsByCategory(@RequestBody Map<String, String> payload) {
		List<Product> results = new ArrayList<Product>();
		
		if(!payload.get("filter").isEmpty()) {
			String filterValue = payload.get("filter");
			System.out.println("filter  " + filterValue);
			if(!payload.get("offset").isEmpty() && !payload.get("limit").isEmpty())
			{
				int offsetValue = Integer.parseInt(payload.get("offset"));
				int limitValue = Integer.parseInt(payload.get("limit"));
		        
		        repository.findAll().forEach(product -> {
		        	long value = Long.parseLong(filterValue);
					if(product.getCategoryId() == value) {
						results.add(product);
					}
				});
		        
		        int fromIndex = offsetValue;
		        int toIndex = Math.min(offsetValue + limitValue, results.size());
		        
				List<Product> filteredResults = results.subList(fromIndex, toIndex);
				
				return new ResponseEntity<>(
						filteredResults, 
		                HttpStatus.OK);
			}
		}
		
		
		return new ResponseEntity<>(
				results, 
                HttpStatus.OK);
	}
	
	@GetMapping("/getProductNameByM3")
	public ResponseEntity<List<Product>> getProductNameByM3(@RequestBody Map<String, String> payload) {
		
		String productName = payload.containsKey("Name") && !payload.get("Name").isEmpty() ? payload.get("Name") : "";
		
		Product p1 = repository.findByName(productName);
		
		List<Product> results = new ArrayList<>();
		
		results.add(p1);
		
		return new ResponseEntity<>(
				results, 
                HttpStatus.OK);
		
	}
	
//	@GetMapping("/getAllCategories")
//	public ResponseEntity<Set<String>> getAllCategories() {
////		List<Product> results = new ArrayList<Product>();
//		Set<String> resultSet = new HashSet<String>();
//		
//		repository.findAll().forEach(product -> {
//			if(product.getCategoryId() != null) {
//				resultSet.add(product.getCategoryId());
//			}
//		});
//		
//		return new ResponseEntity<>(
//				resultSet, 
//                HttpStatus.OK);
//	}
	
	
}