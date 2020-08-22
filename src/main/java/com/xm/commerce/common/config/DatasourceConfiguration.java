package com.xm.commerce.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

	@Primary
	@Bean(name = "ecommerceDataSourceProperties")
	@ConfigurationProperties(prefix = "spring.datasource.ecommerce")
	public DataSourceProperties ecommerceDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean(name = "ecommerceDatasource")
	public DataSource ecommerceDatasource(@Qualifier("ecommerceDataSourceProperties") DataSourceProperties dataSourceProperties) {
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean(name = "uminoDataSourceProperties")
	@ConfigurationProperties(prefix = "spring.datasource.umino")
	public DataSourceProperties uminoDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "uminoDatasource")
	public DataSource uminoDatasource(@Qualifier("uminoDataSourceProperties") DataSourceProperties dataSourceProperties) {
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}
}
