package com.simplilearn.springboot.shoeStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplilearn.springboot.shoeStore.entity.Orders;

public interface OrderRepository extends JpaRepository <Orders, Integer> {

	List<Orders> findByCategoryId(int categoryId);
	
}
