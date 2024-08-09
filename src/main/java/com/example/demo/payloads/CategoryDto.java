package com.example.demo.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;

	@NotBlank
	@Size(min = 4,message = "min size of category is 4")
	private String categoryTitle;

	@NotBlank
	@Size(min = 10,message = "min size of category is 10")
	private String categoryDescription;

}
