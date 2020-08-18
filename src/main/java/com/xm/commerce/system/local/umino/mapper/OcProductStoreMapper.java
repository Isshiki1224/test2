package com.xm.commerce.system.local.umino.mapper;

import com.xm.commerce.system.entity.OcProductStore;
import com.xm.commerce.system.request.CategoryRequest;
import com.xm.commerce.system.response.OcProductStoreResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OcProductStoreMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(OcProductStore record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OcProductStore record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OcProductStore selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OcProductStore record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OcProductStore record);


    List<OcProductStoreResponse> selectByCategory(CategoryRequest categoryRequest);

}