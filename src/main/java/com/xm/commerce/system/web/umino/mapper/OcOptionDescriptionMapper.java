package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcOptionDescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OcOptionDescriptionMapper {
    /**
     * delete by primary key
     *
     * @param optionId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("optionId") Integer optionId, @Param("languageId") Integer languageId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(OcOptionDescription record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcOptionDescription record);

    /**
     * select by primary key
     *
     * @param optionId primary key
     * @return object by primary key
     */
    OcOptionDescription selectByPrimaryKey(@Param("optionId") Integer optionId, @Param("languageId") Integer languageId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcOptionDescription record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcOptionDescription record);

    OcOptionDescription selectByNameAndLanguage(@Param("name") String key, @Param("languageId") int i);

}