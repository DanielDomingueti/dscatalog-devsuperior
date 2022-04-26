package com.domingueti.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.domingueti.dscatalog.entities.Category;
import com.domingueti.dscatalog.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String description;
	
	private Double price;
	
	private String imgUrl;
	
	private Instant date;
	
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO(Product model) {
		this.id = model.getId();
		this.name = model.getName();
		this.description = model.getDescription();
		this.price = model.getPrice();
		this.imgUrl = model.getImgUrl();
		this.date = model.getDate();
	}
	
	public ProductDTO(Product model, Set<Category> categories) {
		this(model);
		categories.forEach(x -> this.categories.add(new CategoryDTO(x)));
	}
}
