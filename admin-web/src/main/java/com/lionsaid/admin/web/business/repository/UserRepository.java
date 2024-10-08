package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SysUser, String> {
    @Transactional
    @Modifying
    @Query("update SysUser s set s.verificationCode = ?1 where s.email = ?2")
    void updateVerificationCodeByEmail(String verificationCode, String email);

    @Transactional
    @Modifying
    @Query("update SysUser u set u.password = ?1 where u.username = ?2 or u.email = ?3")
    int updatePasswordByUsernameOrEmail(String password, String username, String email);

    @Query("select u from SysUser u where  upper(u.username) = upper(?1) or upper(u.email) = upper(?1) or upper(u.mobile) = upper(?1)")
    Optional<SysUser> findByUsername(String username);

    @Query("select u from SysUser u where u.username = ?1 or u.email = ?2 or u.mobile = ?3")
    Optional<SysUser> findByUsernameOrEmailOrMobile(String username, String email, String mobile);

    @Query("select (count(u) > 0) from SysUser u " +
            "where upper(u.username) = upper(?1) or upper(u.email) = upper(?2) or upper(u.mobile) = upper(?3)")
    boolean existsByUsernameIgnoreCaseOrEmailIgnoreCaseOrMobileIgnoreCase(String username, String email, String mobile);

    @Transactional
    @Modifying
    @Query("update SysUser s set s.verificationCode = ?1, s.VerificationCodeExpiryDate = ?2 where s.username = ?3")
    int updateVerificationCodeAndVerificationCodeExpiryDateByUsername(String verificationCode, LocalDateTime VerificationCodeExpiryDate, String username);


}