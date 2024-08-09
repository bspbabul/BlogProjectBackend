package com.example.demo.service;

import java.util.List;

import com.example.demo.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categotyId);
	
	CategoryDto getCategoryId(Integer categotyId);
	
	List<CategoryDto> getallCategory();
	
	public void deleteCategory(Integer categotyId);

}
