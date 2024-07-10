package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysDictRepository extends JpaRepository<SysDict, String> {
    @Query("select s from SysDict s where s.dictIndex = ?1 and s.language = ?2")
    List<SysDict> findByDictIndexAndLanguage(String dictIndex, String language);
}