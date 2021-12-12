package com.simplilearn.springboot.shoeStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplilearn.springboot.shoeStore.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByCategoryId(int id);
}
