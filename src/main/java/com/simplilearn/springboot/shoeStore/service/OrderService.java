package com.simplilearn.springboot.shoeStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.simplilearn.springboot.shoeStore.entity.Orders;
import com.simplilearn.springboot.shoeStore.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	public List <Orders> getAllOrders(@Nullable Integer categoryID) {
		if(categoryID != null) {
			return orderRepository.findByCategoryId(categoryID);
		}
		return orderRepository.findAll();
	}
	
	public void saveOrder(Orders order) {
		orderRepository.save(order);
		return;
	}

}
