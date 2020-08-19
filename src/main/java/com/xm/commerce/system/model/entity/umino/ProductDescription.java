package com.xm.commerce.system.model.entity.umino;

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
public class ProductDescription {
	private Integer productId;

	private Integer languageId;

	private String name;

	private String description;

	private String tag;

	private String metaTitle;

	private String metaDescription;

	private String metaKeyword;
}