package com.ecommerce.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_category")
public class Category implements Serializable {

	@Id
	@Type(type = "uuid-char")
	@GenericGenerator(name = "UUIDGenerator",
			strategy = "uuid2")
	@GeneratedValue(generator = "UUIDGenerator")
	@EqualsAndHashCode.Include
	private UUID uuid;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Builder.Default
	private Date createdAt = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@Builder.Default
	private Date updatedAt = new Date();

}
