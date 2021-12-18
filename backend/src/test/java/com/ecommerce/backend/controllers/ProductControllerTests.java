package com.ecommerce.backend.controllers;

import com.ecommerce.backend.exceptions.DatabaseIntegrityException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.models.response.ProductResponse;
import com.ecommerce.backend.services.ProductServices;
import com.ecommerce.backend.utils.ProductMock;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		Mockito.when(productServices.findAllPaged(ArgumentMatchers.any())).thenReturn(productResponse);

		mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		Mockito.verify(productServices, Mockito.atLeastOnce()).findAllPaged(ArgumentMatchers.any());
	}

	@Test
	public void findByIndexShouldRetrieveObjectWhenUuidExists() throws Exception {
		Mockito.when(productServices.index(TEST_UUID)).thenReturn(ProductMock.createDTO());

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(get(urlTemplate, TEST_UUID).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		assertionsSuccessful(result);
	}

	@Test
	public void findByIndexShouldReturnNotFoundWhenUuidDoesNotExist() throws Exception {
		Mockito.when(productServices.index(TEST_UUID)).thenThrow(ResourceNotFoundException.class);

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(get(urlTemplate, TEST_UUID).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
		assertionsError(result);
	}

	@Test
	public void createShouldCreateObjectWhenRequested() throws Exception {
		ProductDTO productDTO = ProductMock.createDTO();
		String json = objectMapper.writeValueAsString(productDTO);

		Mockito.when(productServices.create(ArgumentMatchers.any())).thenReturn(productDTO);

		String urlTemplate = "/products";
		ResultActions result = mockMvc.perform(post(urlTemplate, TEST_UUID).contentType(MediaType.APPLICATION_JSON).content(json));

		result.andExpect(status().isCreated());
		assertionsSuccessful(result);
	}

	@Test
	public void updateByIndexShouldUpdateObjectWhenUuidExists() throws Exception {
		ProductDTO productDTO = ProductMock.createDTO();
		String json = objectMapper.writeValueAsString(productDTO);

		Mockito.when(productServices.updateByIndex(ArgumentMatchers.eq(TEST_UUID), ArgumentMatchers.any())).thenReturn(ProductMock.createDTO());

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(put(urlTemplate, TEST_UUID).contentType(MediaType.APPLICATION_JSON).content(json));

		result.andExpect(status().isOk());
		assertionsSuccessful(result);
	}

	@Test
	public void updateByIndexShouldReturnNotFoundWhenUuidDoesNotExist() throws Exception {
		ProductDTO productDTO = ProductMock.createDTO();
		String json = objectMapper.writeValueAsString(productDTO);

		Mockito.when(productServices.updateByIndex(ArgumentMatchers.eq(TEST_UUID), ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(put(urlTemplate, TEST_UUID).contentType(MediaType.APPLICATION_JSON).content(json));

		result.andExpect(status().isNotFound());
		assertionsError(result);
	}

	@Test
	public void deleteByIndexShouldUpdateObjectWhenUuidExists() throws Exception {
		Mockito.doNothing().when(productServices).deleteByIndex(TEST_UUID);

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(delete(urlTemplate, TEST_UUID));

		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteByIndexShouldThrowResourceNotFoundExceptionWhenUuidDoesNotExist() throws Exception {
		Mockito.doThrow(ResourceNotFoundException.class).when(productServices).deleteByIndex(TEST_UUID);

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(delete(urlTemplate, TEST_UUID).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
		assertionsError(result);
	}

	@Test
	public void deleteByIndexShouldThrowDatabaseIntegrityExceptionWhenEntryIntegrityWasViolated() throws Exception {
		Mockito.doThrow(DatabaseIntegrityException.class).when(productServices).deleteByIndex(TEST_UUID);

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(delete(urlTemplate, TEST_UUID).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
		assertionsError(result);
	}

	private void assertionsSuccessful(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.uuid").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
		result.andExpect(jsonPath("$.price").exists());
	}

	private void assertionsError(ResultActions result) throws Exception {
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
