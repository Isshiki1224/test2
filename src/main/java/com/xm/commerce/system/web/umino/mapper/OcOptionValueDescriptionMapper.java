package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcOptionValueDescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OcOptionValueDescriptionMapper {
    /**
     * delete by primary key
     *
     * @param optionValueId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("optionValueId") Integer optionValueId, @Param("languageId") Integer languageId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(OcOptionValueDescription record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcOptionValueDescription record);

    /**
     * select by primary key
     *
     * @param optionValueId primary key
     * @return object by primary key
     */
    OcOptionValueDescription selectByPrimaryKey(@Param("optionValueId") Integer optionValueId, @Param("languageId") Integer languageId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcOptionValueDescription record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcOptionValueDescription record);

    OcOptionValueDescription selectByNameAndLanguage(@Param("name") String value, @Param("languageId") Integer languageId);
}