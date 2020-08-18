package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcOption;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OcOptionMapper {
    /**
     * delete by primary key
     * @param optionId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer optionId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcOption record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcOption record);

    /**
     * select by primary key
     * @param optionId primary key
     * @return object by primary key
     */
    OcOption selectByPrimaryKey(Integer optionId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcOption record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcOption record);

    OcOption selectByNameAndLanguage(String key, int i);

}