package com.xm.commerce.system.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.xm.commerce.system.mapper.umino", sqlSessionTemplateRef = "uminoSqlSessionTemplate")
public class MybatisUminoConfiguration {

	@Bean(name = "uminoSqlSessionFactory")
	public SqlSessionFactory uminoSqlSessionFactory(@Qualifier("uminoDatasource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:mapper/umino/**/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "uminoDataSourceTransactionManager")
	public DataSourceTransactionManager uminoDataSourceTransactionManager(@Qualifier("uminoDatasource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "uminoSqlSessionTemplate")
	public SqlSessionTemplate uminoSqlSessionTemplate(@Qualifier("uminoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
