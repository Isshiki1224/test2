package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcProductToCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OcProductToCategoryMapper {
    /**
     * delete by primary key
     * @param productId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("productId") Integer productId, @Param("categoryId") Integer categoryId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcProductToCategory record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcProductToCategory record);
}