package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcProductOptionValue;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OcProductOptionValueMapper {
    /**
     * delete by primary key
     * @param productOptionValueId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer productOptionValueId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcProductOptionValue record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcProductOptionValue record);

    /**
     * select by primary key
     * @param productOptionValueId primary key
     * @return object by primary key
     */
    OcProductOptionValue selectByPrimaryKey(Integer productOptionValueId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcProductOptionValue record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcProductOptionValue record);
}