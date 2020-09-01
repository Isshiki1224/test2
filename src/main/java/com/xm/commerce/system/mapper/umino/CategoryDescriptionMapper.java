package com.xm.commerce.system.mapper.umino;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.umino.OcCategoryDescription;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryDescriptionMapper extends BaseMapper<OcCategoryDescription> {

    List<OcCategoryDescription> selectByNameAndLanguageId(@Param("name") String name, @Param("languageId") int languageId);
}