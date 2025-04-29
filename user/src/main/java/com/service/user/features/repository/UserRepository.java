package com.service.user.features.repository;

import com.service.user.core.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <UserModel,String> {
    boolean existsByEmailAndIsDeletedFalse(String email);
}
