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
public class ProductImage {
	private Integer productImageId;

	private Integer productId;

	private String image;

	private Integer sortOrder;

	private Boolean isRotate;

	private Integer productOptionValueId;
}