package com.xm.commerce.system.entity.umino;

import java.util.Date;
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
public class Category {
	private Integer categoryId;

	private String image;

	private Integer parentId;

	private Boolean top;

	private Integer column;

	private Integer sortOrder;

	private Boolean status;

	private Date dateAdded;

	private Date dateModified;

	private String secondaryImage;

	private String alternativeImage;

	private Boolean isFeatured;
}