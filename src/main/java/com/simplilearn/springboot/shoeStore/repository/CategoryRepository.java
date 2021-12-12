package com.simplilearn.springboot.shoeStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.simplilearn.springboot.shoeStore.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
