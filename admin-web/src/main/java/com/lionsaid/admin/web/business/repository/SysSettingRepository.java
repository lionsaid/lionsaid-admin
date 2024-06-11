package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysSetting;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SysSettingRepository extends JpaRepository<SysSetting, UUID> {
  //  @Cacheable(value = "my-redis-cache2-hours", unless = "#result.get()==null")
    @Query("select s from SysSetting s where s.settingKey = ?1")
    Optional<SysSetting> findBySettingKey(String settingKey);

  @Query("select (count(s) > 0) from SysSetting s where s.settingKey = ?1")
  boolean existsBySettingKey(String settingKey);
}