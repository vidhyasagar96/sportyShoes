package com.simplilearn.springboot.shoeStore.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.simplilearn.springboot.shoeStore.DTO.ChangePassword;
import com.simplilearn.springboot.shoeStore.DTO.ProductDTO;
import com.simplilearn.springboot.shoeStore.entity.Category;
import com.simplilearn.springboot.shoeStore.entity.CustomUserDetail;
import com.simplilearn.springboot.shoeStore.service.CategoryService;
import com.simplilearn.springboot.shoeStore.service.OrderService;
import com.simplilearn.springboot.shoeStore.service.ProductService;
import com.simplilearn.springboot.shoeStore.service.UserService;
import com.simplilearn.springboot.shoeStore.entity.Product;
import com.simplilearn.springboot.shoeStore.entity.User;
import com.simplilearn.springboot.shoeStore.repository.UserRepository;

@Controller
public class AdminController {
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/admin")
	public String adminHome() {
		
		return"adminHome";
	}
	
	@GetMapping("/admin/users")
	public String ShowUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return"users";
	}
	
	@GetMapping("/admin/categories")
	public String categories(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return"categories";
	}
	

	@GetMapping("/admin/categories/add")
	public String addCategories(Model model) {
		
		model.addAttribute("category",new Category());
		return"categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCategories(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return"redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String delCategories(@PathVariable int id) {
		categoryService.delCategory(id);
		return"redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategories(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.updateCategory(id);
		
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		}
		else return "404";
	}
	
	//Product section
	@GetMapping("/admin/products")
	public String Product(Model model) {
		model.addAttribute("products",productService.getAllProduct());
		return"products";
	}
	
	@GetMapping("/admin/products/add")
	public String addProduct(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return"productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String addProductPost(@ModelAttribute("productDTO")ProductDTO productDTO)
								 throws IOException{
		Product product= new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.updateCategory(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setSize(productDTO.getSize());
		product.setDescription(productDTO.getDescription());
		
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String delProduct(@PathVariable long id) {
		productService.delProduct(id);
		return"redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id, Model model) {
		Product product = productService.getProduct(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId((product.getCategory().getId()));
		productDTO.setPrice(product.getPrice());
		productDTO.setSize(product.getSize());
		productDTO.setDescription(product.getDescription());
		
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("productDTO",productDTO);
		
		return "productsAdd";
	}
	
	@GetMapping("/admin/searchUsers")
	public String searchUser(@RequestParam("name") String name, Model model) {
		model.addAttribute("users", userService.getUsersByName(name));
		return "users";
	}
	
	@GetMapping("/admin/changePage")
	public String changePage() {
		
		return "changePage";
	}
	
	@PostMapping("/admin/changePage/changePassword")
	public String changePassword(@ModelAttribute("password_obj") ChangePassword changePassword,
			@AuthenticationPrincipal CustomUserDetail userDetails, HttpServletRequest request) throws ServletException {
		Optional<User> user = userService.getUsersByEmail(userDetails.getUsername());
		
		if(user.isEmpty()) {
			return"redirect:/login/";
		}
		
		if(this.bCryptPasswordEncoder.matches(changePassword.getOldPassword(), user.get().getPassword())) {
			String password=this.bCryptPasswordEncoder.encode(changePassword.getNewPassword());
			user.get().setPassword(password);
			userRepository.save(user.get());
		}
		else
			return "changePage";
		
		return"redirect:/login/";
	}
	
	@GetMapping("/admin/purchaseHistory")
	public String showOrder(@RequestParam("category") @Nullable Integer categoryId,Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("orders", orderService.getAllOrders(categoryId));
		return "purchaseHistory";
	}
}
