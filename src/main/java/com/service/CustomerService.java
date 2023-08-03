package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CustomerRepository;
import com.entity.*;

@Service
public class CustomerService {

	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer saveCustomer(Customer customer)
	{
		return customerRepository.save(customer);
		
	}
	
	public List<Customer> getAllRoom() {
		List<Customer> customer = (List<Customer>) customerRepository.findAll();
		return customer;
	}
	
	
//	 public boolean checkIfCustomerExistsByEmail(String email) {
//	        Customer existingCustomer = (Customer) customerRepository.findByEmail(email);
//	        return existingCustomer != null;
//	    }
	
	
	
}
