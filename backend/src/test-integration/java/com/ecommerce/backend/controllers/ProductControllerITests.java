package com.ecommerce.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.utils.ProductMock;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerITests {

	private final UUID TEST_UUID = UUID.fromString("ba005e67-e457-48f3-8cf9-9e40c7e36a95");

	private final UUID TEST_UUID_INVALID = UUID.fromString("cd662ae5-640a-4605-8bba-45406c198903");

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
		String urlTemplate = "/products?page=0&size=12&sort=name,asc";
		ResultActions result = mockMvc.perform(get(urlTemplate).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.data.content").exists());
		result.andExpect(jsonPath("$.data.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.data.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.data.content[2].name").value("PC Gamer Alfa"));

		result.andExpect(jsonPath("$.data.pageable.pageSize").value(12));
		result.andExpect(jsonPath("$.data.pageable.pageNumber").value(0));
		result.andExpect(jsonPath("$.data.totalElements").value(25));
	}

	@Test
	public void updateByIndexShouldUpdateResourceWhenUuidExists() throws Exception {
		ProductDTO productDTO = ProductMock.createDTO();
		productDTO.setUuid(TEST_UUID);

		String json = objectMapper.writeValueAsString(productDTO);

		String expectedName = productDTO.getName();
		String expectedDescription = productDTO.getDescription();
		double expectedPrice = productDTO.getPrice().doubleValue();

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(put(urlTemplate, TEST_UUID).contentType(MediaType.APPLICATION_JSON).content(json));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.name").value(expectedName));
		result.andExpect(jsonPath("$.description").value(expectedDescription));
		result.andExpect(jsonPath("$.price").value(expectedPrice));
	}

	@Test
	public void updateByIndexShouldReturnNotFoundWhenUuidDoesNotExist() throws Exception {
		ProductDTO productDTO = ProductMock.createDTO();
		String json = objectMapper.writeValueAsString(productDTO);

		String urlTemplate = "/products/{uuid}";
		ResultActions result = mockMvc.perform(put(urlTemplate, TEST_UUID_INVALID).contentType(MediaType.APPLICATION_JSON).content(json));

		result.andExpect(status().isNotFound());
	}

}
