package com.simplilearn.springboot.shoeStore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplilearn.springboot.shoeStore.entity.Category;
import com.simplilearn.springboot.shoeStore.repository.CategoryRepository;


@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	
	public void addCategory(Category category){
		categoryRepository.save(category);
	}
	
	public void delCategory(int id){
		categoryRepository.deleteById(id);
	}
	
	public Optional<Category> updateCategory(int id){
		return categoryRepository.findById(id);
	}
}