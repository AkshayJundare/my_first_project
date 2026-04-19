package com.example.crud.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.entity.Address;
import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;
	@Autowired
	private UserRepository userRepo;

	public UserController(UserService userService) {
//    	logger.debug(""{});
		logger.debug("UserService injected: {}", userService.toString());
		logger.info("Info message");
		this.userService = userService;
System.out.println("wlcome");
//        shas
	}

	// Create a single user
	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
	    User createdUser = userService.createUser(user);
	    return ResponseEntity.status(HttpStatus.CREATED).body("useres is Createded");
	}
	// Create multiple users
	@PostMapping("/batch")
	public String createMultipleUsers(@RequestBody List<User> users) {
		userService.createMultipleUsers(users);
		return "All users saved successfully";
	}

	// Get all users
	@GetMapping
	public List<User> getAllUsers() {
		System.out.println("one time");
		return userService.getAllUsers();
	}

	// Update user by ID
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User user) {
//        return userService.updateUser(id, user);
//    }

	// Delete user by ID
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "User deleted successfully";
	}

	////
	@GetMapping("/readonly")
	public String readOnlyTransaction() {
		userService.readOnlyTransactionDemo();
		return "Read-only transaction executed (user will NOT be saved).";
	}

	////
	// Find users by name
	@GetMapping("/search/{name}")
	public List<User> getUsersByName(@PathVariable String name) {
		System.out.println("name is here" + name);
		List<User> list = userService.getUsersByName(name);
		System.out.println("ki" + list);
		return userService.getUsersByName(name);
	}

	///
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUsers(@PathVariable Long id, @RequestBody User user) {
		User updatedUser = userService.updateUsers(id, user);
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserByIdd(@PathVariable Long id) {
		User user = userService.getUserById(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	////
	// ===== NEW: Address Endpoints =====

	// Add Address to User
	@PostMapping("/{id}/addresses")
	public Address addAddress(@PathVariable Long id, @RequestBody Address address) {
		return userService.addAddressToUser(id, address);
	}

	// Demo Lazy/Eager Loading
	@GetMapping("/{id}/addresses")
	public String demoLazyEager(@PathVariable Long id) {
		userService.demoLazyLoading(id);
		return "Check console for Lazy/Eager loading SQL queries.";
	}

	@GetMapping("/byname/{name}")
	public ResponseEntity<User> getUserByName(@PathVariable String name) {
		return userService.getUserName(name).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

	}
	
	
	@GetMapping("/email/{email}")
	public User getUserMail(@PathVariable String email){
		
		logger.info("entering in getUserMail method{}",email);
	User user=	userService.getUserByEmail(email);
		logger.info("User in getUserMail method:{}",user);
		return userService.getUserByEmail(email);
		
		
		
		
	}
	
}
