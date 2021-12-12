package com.simplilearn.springboot.shoeStore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.simplilearn.springboot.shoeStore.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findUserByEmail(String email);
	
	List<User> findByNameStartingWith(String name);
	
	@Query("SELECT u FROM User u WHERE u.name = :name")
    public User getUserByUsername(@Param("name") String name);
	
}