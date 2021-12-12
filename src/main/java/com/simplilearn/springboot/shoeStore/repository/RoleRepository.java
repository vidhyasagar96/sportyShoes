package com.simplilearn.springboot.shoeStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplilearn.springboot.shoeStore.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>{

}

