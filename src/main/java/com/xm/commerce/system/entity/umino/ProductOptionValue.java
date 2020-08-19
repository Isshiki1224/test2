package com.xm.commerce.system.entity.umino;

import java.math.BigDecimal;
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
public class ProductOptionValue {
	private Integer productOptionValueId;

	private Integer productOptionId;

	private Integer productId;

	private Integer optionId;

	private Integer optionValueId;

	private Integer quantity;

	private Boolean subtract;

	private BigDecimal price;

	private String pricePrefix;

	private Integer points;

	private String pointsPrefix;

	private BigDecimal weight;

	private String weightPrefix;
}