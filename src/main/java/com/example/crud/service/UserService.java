package com.example.crud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crud.entity.Address;
import com.example.crud.entity.User;
import com.example.crud.repository.AddressRepository;
import com.example.crud.repository.UserRepository;

@Service
public class UserService {

	private final AddressRepository addressRepository; // NEW
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository; // NEW
	}

	// Create a single user
	@Transactional
	public User createUser(User user) {
		return userRepository.save(user);
	}

	// Create multiple users in one transaction
	@Transactional
	public void createMultipleUsers(List<User> users) {
		for (User user : users) {
			userRepository.save(user);

			// Simulate error
			if ("error".equalsIgnoreCase(user.getName())) {
				throw new RuntimeException("Simulated error!");
			}
		}
	}

	// Read all users (read-only transaction)
	@Transactional(readOnly = true)
	@Cacheable("users")
	public List<User> getAllUsers() {
		System.out.println("Fetching from database one...");
		return userRepository.findAll();
	}

	// Update user by ID
	@Transactional
	public User updateUser(Long id, User user) {
		User existing = userRepository.findById(id).orElse(null);
		if (existing != null) {
			existing.setName(user.getName());
			existing.setEmail(user.getEmail());
			return userRepository.save(existing);
		}
		return null;
	}

	// Delete user by ID
	@Transactional
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	/// // Read-only transaction (cannot write)
	@Transactional(readOnly = true)
	public void readOnlyTransactionDemo() {
		System.out.println("Read-only transaction started...");
		User user = new User();
		user.setName("ReadOnlyUser");
		user.setEmail("readonly@example.com");
		System.out.println("fail");
		userRepository.save(user); // Will NOT persist due to readOnly=true
		System.out.println("Attempted to save in read-only transaction");

	}

	public List<User> getUsersByName(String name) {
		System.out.println("lom" + name);
		List<User> list = new ArrayList();
//        return userRepository.findByNameIgnoreCase(name);
		return list;
	}

	///
//    @Transactional
	@Transactional
	@CachePut(value = "user", key = "#id")
//    @CacheEvict(value = "users", allEntries = true)
	public User updateUsers(Long id, User user) {
		User existing = userRepository.findById(id).orElse(null);
		if (existing != null) {
			existing.setName(user.getName());
			existing.setEmail(user.getEmail());
			return userRepository.save(existing); // Also updates cache
		}
		return null;
	}

	////
	@Transactional(readOnly = true)
	@Cacheable(value = "user", key = "#id")
	public User getUserById(Long id) {
		System.out.println("Fetching user from database...");
		return userRepository.findById(id).orElse(null);
	}
	// ===== NEW: Lazy/Eager Address Methods =====

	// Add Address to User
	@Transactional
	public Address addAddressToUser(Long userId, Address address) {
		User user = userRepository.findById(userId).orElseThrow();
		address.setUser(user);
		user.getAddresses().add(address); // cascade saves automatically
		return addressRepository.save(address);
	}

	// Demo Lazy Loading
	@Transactional(readOnly = true)
	public void demoLazyLoading(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			System.out.println("User Name: " + user.getName());
			System.out.println("Addresses count: " + user.getAddresses().size());
			// SQL for addresses executes here if LAZY
		}
	}

	public Optional<User> getUserName(String name) {

		return userRepository.findByNameIgnoreCase(name);

	}
	 @Transactional
	    public User getUserByEmail(String email) {
	        return userRepository.getUserByEmail(email);
	    }
}
