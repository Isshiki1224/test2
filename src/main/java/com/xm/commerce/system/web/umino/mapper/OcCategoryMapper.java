package com.xm.commerce.system.web.umino.mapper;

import com.xm.commerce.system.entity.OcCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OcCategoryMapper {
    /**
     * delete by primary key
     *
     * @param categoryId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer categoryId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(OcCategory record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcCategory record);

    /**
     * select by primary key
     *
     * @param categoryId primary key
     * @return object by primary key
     */
    OcCategory selectByPrimaryKey(Integer categoryId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcCategory record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcCategory record);
}