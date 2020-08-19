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
public class ProductAttribute {
	private Integer productId;

	private Integer attributeId;

	private Integer languageId;

	private String text;
}