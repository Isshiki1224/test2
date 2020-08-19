package com.xm.commerce.system.entity.umino;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDescription {
	private Integer categoryId;

	private Integer languageId;

	private String name;

	private String description;

	private String metaTitle;

	private String metaDescription;

	private String metaKeyword;
}