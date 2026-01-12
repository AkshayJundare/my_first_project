package com.example.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.entity.Users;

//import com.example.crud.User.Users;

public interface UserRepo extends JpaRepository<Users, Long> {
	Optional<Users> findByUsername(String username);

}
