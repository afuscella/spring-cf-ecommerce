package com.ecommerce.backend.models.response;

import org.springframework.data.domain.Page;

import com.ecommerce.backend.models.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryResponse {

	private final Page<CategoryDTO> data;

}
