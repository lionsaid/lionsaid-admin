package com.lionsaid.admin.web.repository;

import com.lionsaid.admin.web.enums.LionSaidStatusFlag;
import com.lionsaid.admin.web.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Transactional
    @Modifying
    @Query("update User u set u.password = ?1 where u.username = ?2 or u.email = ?3")
    int updatePasswordByUsernameOrEmail(String password, String username, String email);

    @Query("select u from User u where  upper(u.username) = upper(?1) or upper(u.email) = upper(?1) or upper(u.mobile) = upper(?1)")
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username = ?1 or u.email = ?2 or u.mobile = ?3")
    Optional<User> findByUsernameOrEmailOrMobile(String username, String email, String mobile);

    @Query("select (count(u) > 0) from User u " +
            "where upper(u.username) = upper(?1) or upper(u.email) = upper(?2) or upper(u.mobile) = upper(?3)")
    boolean existsByUsernameIgnoreCaseOrEmailIgnoreCaseOrMobileIgnoreCase(String username, String email, String mobile);

    @Transactional
    @Modifying
    @Query("update User u set u.emailVerify = ?1 where u.email = ?2")
    void updateEmailVerifyByEmail(Integer emailVerify, String email);
}