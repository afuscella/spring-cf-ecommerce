package com.ecommerce.backend.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

	@Id
	@Type(type = "uuid-char")
	@GenericGenerator(name = "UUIDGenerator",
			strategy = "uuid2")
	@GeneratedValue(generator = "UUIDGenerator")
	@EqualsAndHashCode.Include
	private UUID uuid;

	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	private BigDecimal price;

	private String imgUrl;

	@ManyToMany
	@JoinTable(name = "tb_product_category",
			joinColumns = @JoinColumn(name = "product_uuid"),
			inverseJoinColumns = @JoinColumn(name = "category_uuid"))
	private Set<Category> categories = new HashSet<>();

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	@Builder.Default
	private Instant createdAt = Instant.now();

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	@Builder.Default
	private Instant updatedAt = Instant.now();

	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}

}
