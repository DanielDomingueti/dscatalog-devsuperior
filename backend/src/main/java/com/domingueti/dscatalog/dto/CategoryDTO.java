package com.domingueti.dscatalog.dto;

import java.io.Serializable;

import com.domingueti.dscatalog.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public CategoryDTO(Category model) {
		this.id = model.getId();
		this.name = model.getName();
	}
	
}
