package com.xm.commerce.system.mapper.umino;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.umino.OcCategoryToStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OcCategoryToStoreMapper extends BaseMapper<OcCategoryToStore> {

    List<OcCategoryToStore> selectByCategoryIdAndStoreId(OcCategoryToStore categoryToStore);

}