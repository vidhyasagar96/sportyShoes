package com.simplilearn.springboot.shoeStore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplilearn.springboot.shoeStore.entity.Product;
import com.simplilearn.springboot.shoeStore.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProduct(){
		return productRepository.findAll();
	}
	
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	public void delProduct(long id) {
		productRepository.deleteById(id);
	}
	
	public Optional<Product> getProduct(long id){
		
		return productRepository.findById(id);
	}
	
	public List<Product> getAllProductsByCategory(int id){
		return productRepository.findAllByCategoryId(id);
	}

}
