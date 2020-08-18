package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcProductImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OcProductImageMapper {
    /**
     * delete by primary key
     * @param productImageId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer productImageId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcProductImage record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcProductImage record);

    /**
     * select by primary key
     * @param productImageId primary key
     * @return object by primary key
     */
    OcProductImage selectByPrimaryKey(Integer productImageId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcProductImage record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcProductImage record);
}