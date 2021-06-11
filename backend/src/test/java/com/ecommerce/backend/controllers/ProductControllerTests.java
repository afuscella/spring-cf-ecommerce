package com.ecommerce.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.models.response.ProductResponse;
import com.ecommerce.backend.services.ProductServices;
import com.ecommerce.backend.utils.ProductMock;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

	static final UUID TEST_UUID = UUID.randomUUID();

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ProductServices productServices;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void findAllShouldReturnPageWhenRequested() throws Exception {
		ProductResponse productResponse = productResponseMock();
		Mockito.when(productServices.handleAllPaged(ArgumentMatchers.any())).thenReturn(productResponse);

		mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		Mockito.verify(productServices, Mockito.atLeastOnce()).handleAllPaged(ArgumentMatchers.any());
	}

	@Test
	public void findByIndexShouldRetrieveObjectWhenUuidExists() throws Exception {
		Mockito.when(productServices.handleIndex(TEST_UUID)).thenReturn(ProductMock.createDTO());

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(get(urlTemplate, TEST_UUID).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		assertionsOk(result);
	}

	@Test
	public void findByIndexShouldReturnNotFoundWhenUuidDoesNotExist() throws Exception {
		Mockito.when(productServices.handleIndex(TEST_UUID)).thenThrow(ResourceNotFoundException.class);

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(get(urlTemplate, TEST_UUID).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
		assertionsNotFound(result);
	}

	@Test
	public void updateByIndexShouldUpdateObjectWhenUuidExists() throws Exception {
		ProductDTO productDTO = ProductMock.createDTO();
		String json = objectMapper.writeValueAsString(productDTO);

		Mockito.when(productServices.handleUpdateByIndex(ArgumentMatchers.eq(TEST_UUID), ArgumentMatchers.any())).thenReturn(ProductMock.createDTO());

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(put(urlTemplate, TEST_UUID).contentType(MediaType.APPLICATION_JSON).content(json));
		assertionsOk(result);
	}

	@Test
	public void updateByIndexShouldReturnNotFoundWhenUuidDoesNotExist() throws Exception {
		ProductDTO productDTO = ProductMock.createDTO();
		String json = objectMapper.writeValueAsString(productDTO);

		Mockito.when(productServices.handleUpdateByIndex(ArgumentMatchers.eq(TEST_UUID), ArgumentMatchers.any()))
				.thenThrow(ResourceNotFoundException.class);

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(put(urlTemplate, TEST_UUID).contentType(MediaType.APPLICATION_JSON).content(json));
		assertionsNotFound(result);
	}

	private void assertionsOk(ResultActions result) throws Exception {
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.uuid").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
		result.andExpect(jsonPath("$.price").exists());
	}

	private void assertionsNotFound(ResultActions result) throws Exception {
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.timestamp").exists());
		result.andExpect(jsonPath("$.status").exists());
		result.andExpect(jsonPath("$.error").exists());
		result.andExpect(jsonPath("$.message").isEmpty());
		result.andExpect(jsonPath("$.path").exists());
	}

	private ProductResponse productResponseMock() {
		PageImpl<ProductDTO> page = new PageImpl<>(List.of(ProductMock.createDTO()));
		return new ProductResponse(page);
	}

}
