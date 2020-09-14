package com.xm.commerce.system.mapper.umino;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.umino.OcProductToStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OcProductToStoreMapper extends BaseMapper<OcProductToStore> {

    List<OcProductToStore> selectByProductIdAndStoreId(OcProductToStore ocProductToStore);
}