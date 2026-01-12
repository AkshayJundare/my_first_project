package com.example.crud.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

//import com.example.crud.User.Users;
import com.example.crud.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByName(String username);

	Optional<User> findByNameIgnoreCase(String name);
	
	 @Procedure(procedureName = "get_user_by_email")
	 User getUserByEmail(@Param("userEmail") String email);

}
