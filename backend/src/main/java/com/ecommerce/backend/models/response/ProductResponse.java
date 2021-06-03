package com.ecommerce.backend.models.response;

import org.springframework.data.domain.Page;

import com.ecommerce.backend.models.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductResponse {

	private final Page<ProductDTO> data;

}
