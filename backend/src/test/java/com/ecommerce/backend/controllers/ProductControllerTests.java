package com.ecommerce.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.models.response.ProductResponse;
import com.ecommerce.backend.services.ProductServices;
import com.ecommerce.backend.utils.ProductMock;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ProductServices productServices;

	@Test
	public void productControllerShouldReturnPageWhenRequested() throws Exception {
		ProductResponse productResponse = productResponseMock();
		Mockito.when(productServices.handleAllPaged(ArgumentMatchers.any())).thenReturn(productResponse);

		mockMvc.perform(get("/products")).andExpect(status().isOk());
		Mockito.verify(productServices, Mockito.atLeastOnce()).handleAllPaged(ArgumentMatchers.any());
	}

	private ProductResponse productResponseMock() {
		PageImpl<ProductDTO> page = new PageImpl<>(List.of(ProductMock.createDTO()));
		return new ProductResponse(page);
	}

}
