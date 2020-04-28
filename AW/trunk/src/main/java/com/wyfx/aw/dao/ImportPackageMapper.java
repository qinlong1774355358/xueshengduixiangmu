package com.wyfx.aw.dao;

import com.wyfx.aw.entity.ImportPackage;

import java.util.List;

public interface ImportPackageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImportPackage record);

    int insertSelective(ImportPackage record);

    ImportPackage selectByPrimaryKey(Integer id);

    List<ImportPackage> selectImportPackage(ImportPackage importPackage);

    int updateByPrimaryKeySelective(ImportPackage record);

    int updateByPrimaryKey(ImportPackage record);
}