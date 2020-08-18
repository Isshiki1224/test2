package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcCategoryDescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OcCategoryDescriptionMapper {
    /**
     * delete by primary key
     * @param categoryId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("categoryId") Integer categoryId, @Param("languageId") Integer languageId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OcCategoryDescription record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcCategoryDescription record);

    /**
     * select by primary key
     * @param categoryId primary key
     * @return object by primary key
     */
    OcCategoryDescription selectByPrimaryKey(@Param("categoryId") Integer categoryId, @Param("languageId") Integer languageId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcCategoryDescription record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcCategoryDescription record);

    OcCategoryDescription selectByNameAndLanguage(@Param("name") String categoryName, @Param("languageId") Integer language);
}