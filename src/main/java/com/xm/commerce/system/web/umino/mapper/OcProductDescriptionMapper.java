package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcProductDescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OcProductDescriptionMapper {
    /**
     * delete by primary key
     * @param productId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("productId") Integer productId, @Param("languageId") Integer languageId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcProductDescription record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcProductDescription record);

    /**
     * select by primary key
     * @param productId primary key
     * @return object by primary key
     */
    OcProductDescription selectByPrimaryKey(@Param("productId") Integer productId, @Param("languageId") Integer languageId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcProductDescription record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcProductDescription record);
}