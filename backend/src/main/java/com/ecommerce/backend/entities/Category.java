package com.ecommerce.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category implements Serializable {

	@EqualsAndHashCode.Include
	private UUID uuid;

	private String name;

	@Builder.Default
	private Date createdAt = new Date();

	@Builder.Default
	private Date updatedAt = new Date();

}
