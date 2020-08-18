package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OcProductAttributeMapper {
    /**
     * delete by primary key
     * @param productId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId, @Param("languageId") Integer languageId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcProductAttribute record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcProductAttribute record);

    /**
     * select by primary key
     * @param productId primary key
     * @return object by primary key
     */
    OcProductAttribute selectByPrimaryKey(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId, @Param("languageId") Integer languageId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcProductAttribute record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcProductAttribute record);
}