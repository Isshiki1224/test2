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
public class ProductOption {
	private Integer productOptionId;

	private Integer productId;

	private Integer optionId;

	private String value;

	private Boolean required;
}