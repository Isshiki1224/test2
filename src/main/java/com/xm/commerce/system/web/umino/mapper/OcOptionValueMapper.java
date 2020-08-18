package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcOptionValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OcOptionValueMapper {
    /**
     * delete by primary key
     * @param optionValueId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer optionValueId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcOptionValue record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcOptionValue record);

    /**
     * select by primary key
     * @param optionValueId primary key
     * @return object by primary key
     */
    OcOptionValue selectByPrimaryKey(Integer optionValueId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcOptionValue record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcOptionValue record);

    List<OcOptionValue> selectByOptionId(Integer optionId);
}