package com.simplilearn.springboot.shoeStore.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.simplilearn.springboot.shoeStore.entity.CustomUserDetail;
import com.simplilearn.springboot.shoeStore.entity.Orders;
import com.simplilearn.springboot.shoeStore.entity.Product;
import com.simplilearn.springboot.shoeStore.entity.User;
import com.simplilearn.springboot.shoeStore.service.CategoryService;
import com.simplilearn.springboot.shoeStore.service.OrderService;
import com.simplilearn.springboot.shoeStore.service.ProductService;
import com.simplilearn.springboot.shoeStore.service.UserService;


@Controller
public class HomeController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@GetMapping({"/", "/home"})
	public String home(Model model) {
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("products",productService.getAllProduct());
		return "shop";
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("products",productService.getAllProductsByCategory(id));
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable int id) {
		model.addAttribute("product",productService.getProduct(id).get());
		return "viewProduct";
	}
	
	@PostMapping("/shop/order/{id}")
	public String orderProduct(@PathVariable int id, @AuthenticationPrincipal CustomUserDetail userDetails) {
		Product product = productService.getProduct(id).get();
		Optional<User> user = userService.getUsersByEmail(userDetails.getUsername());
		
		if(user.isEmpty()) {
			return"redirect:/login/";
		}
		
		Orders order = new Orders();
		order.setProductName(product.getName());
		order.setEmail(user.get().getEmail());
		order.setUsername(user.get().getName());
		order.setOrderDate(LocalDateTime.now());
		order.setCategory(product.getCategory());
		orderService.saveOrder(order);
		return "orderConfirm";
	}

}
