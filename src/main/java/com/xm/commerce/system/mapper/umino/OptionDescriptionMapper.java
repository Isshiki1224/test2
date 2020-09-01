package com.xm.commerce.system.mapper.umino;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.umino.OcOptionDescription;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OptionDescriptionMapper extends BaseMapper<OcOptionDescription> {


    List<OcOptionDescription> selectByNameAndLanguageId(@Param("name") String name, @Param("languageId") int languageId);
}