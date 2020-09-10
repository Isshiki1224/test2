package com.xm.commerce.common.datasource.util;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.xm.commerce.common.exception.SiteMessageException;
import com.xm.commerce.system.model.entity.ecommerce.DataSourceConfig;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
public class LoadDataSourceUtil {

	@Resource
	private DataSource dataSource;
	@Resource
	private DataSourceCreator dataSourceCreator;

	public CopyOnWriteArraySet<String> now() {
		DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
//		CopyOnWriteArraySet
		return new CopyOnWriteArraySet<>(ds.getCurrentDataSources().keySet());
	}

	public boolean add(EcommerceSite record) throws SiteMessageException {
		CopyOnWriteArraySet<String> now = now();
		for (String s : now) {
			if (record.getDbName().equals(s)) {
				return false;
			}
		}
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		DataSourceConfig config = DataSourceConfig.builder()
				.poolName(record.getDbName())
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.password(record.getDbPassword())
				.username(record.getDbUsername())
				.url("jdbc:mysql://" + record.getIp() + ":" + record.getPort() + "/" + record.getDbName() + "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8")
				.build();
		BeanUtils.copyProperties(config, dataSourceProperty);
		DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
		DataSource dataSource;
		try {
			dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
		} catch (Exception e) {
			throw new SiteMessageException();
		}
		ds.addDataSource(config.getPoolName(), dataSource);
		return true;
	}

	public boolean remove(String name) {
		CopyOnWriteArraySet<String> now = now();
		for (String s : now) {
			if (name.equals(s)) {
				DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
				ds.removeDataSource(name);
				log.info("delete success");
				return true;
			}
		}
		log.info("could not find a database named [{}]", name);
		return false;
	}

}
