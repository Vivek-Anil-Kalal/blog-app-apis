package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.impl.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto category = this.categoryServiceImpl.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(category,HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer id){
		
		CategoryDto updatedCategory = this.categoryServiceImpl.updateCategory(categoryDto,id);
		
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
		
		this.categoryServiceImpl.deleteCategory(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted sucessfully" , true) 
				, HttpStatus.OK);
	}


	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id){
		
		CategoryDto category = this.categoryServiceImpl.getSingleCategory(id);
		
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		
		List<CategoryDto> categorires = this.categoryServiceImpl.getAllCategories();
		
		return new ResponseEntity<List<CategoryDto>>(categorires, HttpStatus.OK);
	}

}
