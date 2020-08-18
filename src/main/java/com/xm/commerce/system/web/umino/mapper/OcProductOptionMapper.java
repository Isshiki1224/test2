package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcProductOption;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OcProductOptionMapper {
    /**
     * delete by primary key
     * @param productOptionId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer productOptionId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcProductOption record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcProductOption record);

    /**
     * select by primary key
     * @param productOptionId primary key
     * @return object by primary key
     */
    OcProductOption selectByPrimaryKey(Integer productOptionId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcProductOption record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcProductOption record);
}