package com.xm.commerce.system.model.entity.ecommerce;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceConfig {

    @NotBlank
    @ApiModelProperty(value = "连接池名称", example = "test")
    private String poolName;

    @NotBlank
    @ApiModelProperty(value = "JDBC driver", example = "org.h2.Driver")
    private String driverClassName;

    @NotBlank
    @ApiModelProperty(value = "JDBC url 地址", example = "jdbc:h2:mem:test10")
    private String url;

    @NotBlank
    @ApiModelProperty(value = "JDBC 用户名", example = "sa")
    private String username;

    @ApiModelProperty(value = "JDBC 密码")
    private String password;
}
