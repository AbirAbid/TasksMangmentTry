package com.tasksmangment.pfa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.tasksmangment.pfa.models.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
	 User findByUsername(String username);
	 Boolean existsByUsername(String username);
	 Boolean existsByEmail(String email);
	 User getByUsername(String username);
}
