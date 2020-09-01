package com.xm.commerce.system.model.entity.ecommerce;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class EcommerceUser {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    private Boolean enabled;
}