package com.ecommerce.backend.models.response;

import java.util.List;

import com.ecommerce.backend.models.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryResponse {

	private final List<CategoryDTO> data;

}
