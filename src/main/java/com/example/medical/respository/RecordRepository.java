package com.example.accessingdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.accessingdatajpa.entity.Customer;
import com.example.accessingdatajpa.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Method to find a user by username
	Product findByName(String name);
	
	Product findById(long id);

    // Other custom queries can be defined here if needed
}
