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
public class OptionValue {
	private Integer optionValueId;

	private Integer optionId;

	private String image;

	private Integer sortOrder;
}