package com.xm.commerce.system.mapper.umino;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.umino.OcCategory;

public interface CategoryMapper extends BaseMapper<OcCategory> {

    int insertSelective(OcCategory ocCategory);

}
