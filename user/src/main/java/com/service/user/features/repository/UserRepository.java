package com.service.user.features.repository;

import com.service.user.core.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <UserModel,String> {
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<UserModel> findByIdAndIsDeletedFalse(String id);
    Optional<UserModel> findByIdAndIsDeletedTrue(String userId);

    @Query("SELECT u FROM UserModel u WHERE u.isDeleted = false ORDER BY u.createdAt DESC")
    Page<UserModel> findAllActiveUsers(Pageable pageable);
}
