package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcProduct;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OcProductMapper {
    /**
     * delete by primary key
     * @param productId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer productId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcProduct record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcProduct record);

    /**
     * select by primary key
     * @param productId primary key
     * @return object by primary key
     */
    OcProduct selectByPrimaryKey(Integer productId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcProduct record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcProduct record);
}