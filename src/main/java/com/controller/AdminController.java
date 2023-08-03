package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Exception.ResourceNotFoundException;
import com.dao.AdminRepo;
import com.entity.Admin;
import com.entity.ApiResponse;
import com.security.JwtUtil;
import com.service.AdminService;

import io.swagger.annotations.ApiOperation;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	public AdminService adminService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public AdminRepo adminRepo;
	 
	@Autowired
	private JwtUtil jwtUtil;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addMembers")    
	public ResponseEntity<?> addUsers(@Valid @RequestBody Admin admin) {
	   

	    if (adminRepo.findByAdEmail(admin.getAdEmail()) != null) {
	        return new ResponseEntity<>(new ApiResponse(false, "This email is already registered."), HttpStatus.BAD_REQUEST);
	    }
	 // Encode the password
	    String encodedPassword = passwordEncoder.encode(admin.getAdPassword());
	    admin.setAdPassword(encodedPassword);

	    Admin createdAdmin = adminRepo.save(admin);
	    if (createdAdmin != null) {
	    	JSONObject obj1 = new JSONObject();
	        obj1.put("message","Staff member Added");
	        obj1.put("code",201);
	        
	    	//String successMessage = "Staff Members Add Successfully.";
	        return ResponseEntity.status(HttpStatus.CREATED).body(obj1);
	    } else {
	        String errorMessage = "Failed to add member.";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	    }
	}


//	@PostMapping("/addMembers")
//	public String addUsers(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult, Model model) {
//
//	    if (bindingResult.hasErrors()) {
//	        return "error"; // Return to the form with validation errors
//	    }
//
//	    if (adminRepo.findByAdEmail(admin.getAdEmail()) != null) {
//	        model.addAttribute("message", "This email is already registered.");
//	        return "error"; // Return to an error page with the error message
//	    }
//
//	    // Encode the password
//	    String encodedPassword = passwordEncoder.encode(admin.getAdPassword());
//	    admin.setAdPassword(encodedPassword);
//
//	    Admin createdAdmin = adminRepo.save(admin);
//	    if (createdAdmin != null) {
//	        model.addAttribute("message", "Staff member added successfully.");
//	        System.out.println("Member Added Successfully");
//	        return "success"; // Return to a success page with the success message
//	    } else {
//	        model.addAttribute("message", "Failed to add member.");
//	        return "error"; // Return to an error page with the error message
//	    }
//	}
	
	
	//@PreAuthorize("hasRole('ADMIN') OR hasRole('STAFF') OR hasRole('MANAGER')")
	@PostMapping("/signinMember")
	public ResponseEntity<?> signinAdmin(@RequestBody Admin admin) throws Exception {
		try {
			
			 Admin existingAdmin = adminService.getUserByEmail(admin.getAdEmail());
			 
			
			if (existingAdmin == null) {
	            throw new ResourceNotFoundException("User", "Email", "This Email is Incorrect.");
	        }

			 // Compare the entered password with the decrypted stored password
	        if (!passwordEncoder.matches(admin.getAdPassword(), existingAdmin.getAdPassword())) {
	            throw new ResourceNotFoundException("User", "Password", "This Password is Incorrect");
	        }
 
	        int role = existingAdmin.getRole();
	        JSONObject jsonResponse = new JSONObject();
	        String successMessage;
	        String jwtToken; 
	       String roles;
	       
	        if (role == 1) {
	            
	            roles = "admin";
	            successMessage = "Admin Login Successfully";
	            
	            System.out.println("Admin Login");
	            
	        } else if (role ==2) {
	        	roles = "manager";
	            successMessage = "Manager Login Successfully";
	            
	            System.out.println("Manager Login");
	            
	        } else if (role == 0) {
	        	roles = "staff";
	            successMessage = "staff Login Successfully";
	            System.out.println("Staff Login");
	        } else {
	            
	            throw new ResourceNotFoundException("Admin", "Role", "Unrecognized Role.");
	        } 
	         
	        
	        jwtToken = jwtUtil.generateToken(existingAdmin);
	        jsonResponse.put("Status", true);
	        jsonResponse.put("role", roles);
	            jsonResponse.put("successMessage", successMessage);
	            jsonResponse.put(" Bearer Token", jwtToken);

	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse);
		} catch (ResourceNotFoundException ex) {
		    return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAllMember")
	public List<Admin> getAllUser()
	{
		List<Admin> admin = (List<Admin>) adminRepo.findAll();
		System.out.println("demo");
		return admin;
	}


//	@PreAuthorize("hasRole('ADMIN')")
//	@DeleteMapping("/deleteMemberById/{id}")
//	public String deleteAdmin(@PathVariable("id") long adminId ) {
//		
//		return adminService.deleteAdmin(adminId);
//	}
	@PostMapping("/deleteMemberById/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteAdmin(@PathVariable("id") long adminId) {
	    // Check if the admin with the given adminId exists in the database
	    Optional<Admin> existingAdminOptional = adminRepo.findById(adminId);

	    if (existingAdminOptional.isPresent()) {
	        Admin existingAdmin = existingAdminOptional.get();
	        adminRepo.delete(existingAdmin);
	        System.out.println("Admin Deleted");
	        return "Admin with ID " + adminId + " has been deleted successfully.";
	    } else {
	        // Admin does not exist, return an error message
	        return "Admin with ID " + adminId + " does not exist.";
	    }
	}

	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findMemberByEmail/{email}")
	public Admin getUserByEmail(@PathVariable("email") String email)
	{
		
		return adminRepo.findByAdEmail(email);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findMemberByName/{name}")
	public Admin getUserByName(@PathVariable("name") String name)
	{
		return adminRepo.findByAdName(name);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findMemberByRole/{role}")
	public List<Admin> getUserByRole(@PathVariable("role")  int role)
	{
		List<Admin> admin = adminRepo.findByRole(role);
		return admin; 
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteAllMember")
	public String deleteAllAdmins() {
	    String message = null;

	    long count = adminRepo.count();
	    if (count > 0) {
	        adminRepo.deleteAll();
	        message = "All admins deleted successfully!";
	    } else {
	        throw new ResourceNotFoundException("Admins", "No admins found to delete.", count);
	    }

	    return message;
	}
	


}
