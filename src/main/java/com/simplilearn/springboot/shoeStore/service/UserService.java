package com.simplilearn.springboot.shoeStore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplilearn.springboot.shoeStore.entity.User;
import com.simplilearn.springboot.shoeStore.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public List<User> getUsersByName(String name){
		return userRepository.findByNameStartingWith(name);
	}
	

	public Optional<User> getUsersByEmail(String email){
		return userRepository.findUserByEmail(email);
	}
}
