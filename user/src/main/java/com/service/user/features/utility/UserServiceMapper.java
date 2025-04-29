package com.service.user.features.utility;

import com.password4j.Hash;
import com.password4j.Password;
import com.service.user.core.exceptions.GlobalDurinUserServiceException;
import com.service.user.core.model.UserModel;
import com.service.user.features.dto.UserDto;

import java.util.Objects;

public class UserServiceMapper {

    public static UserModel mapDtoToEntity(UserDto dto) {
        if (Objects.isNull(dto)) {
            throw new GlobalDurinUserServiceException("UserServiceMapper: Provided UserDto is null.");
        }
        validateRequiredFields(dto);
        UserModel entity = new UserModel();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        // Secure password hashing with Argon2
        Hash hash = Password.hash(dto.getPassword())
                        .addRandomSalt()
                        .withArgon2();
        entity.setPassword(hash.getResult());

        entity.setRole(dto.getRole());
        entity.setProfilePictureUrl(dto.getProfilePictureUrl());
        entity.setEmailVerified(dto.getEmailVerified());
        entity.setIsActive(Objects.requireNonNullElse(dto.getIsActive(), true));
        entity.setIsDeleted(Objects.requireNonNullElse(dto.getIsDeleted(), false));
        return entity;
    }

    private static void validateRequiredFields(UserDto dto) {
        if (Objects.isNull(dto.getFirstName()) || dto.getFirstName().isBlank()) {
            throw new GlobalDurinUserServiceException("UserServiceMapper: First name is required.");
        }
        if (Objects.isNull(dto.getEmail()) || dto.getEmail().isBlank()) {
            throw new GlobalDurinUserServiceException("UserServiceMapper: Email is required.");
        }
    }


    public static UserDto mapEntityToDto(UserModel entity) {
        if (Objects.isNull(entity)) {
            throw new GlobalDurinUserServiceException("UserServiceMapper: Provided UserModel is null.");
        }
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setProfilePictureUrl(entity.getProfilePictureUrl());
        dto.setEmailVerified(entity.getEmailVerified());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDeleted(entity.getIsDeleted());
        return dto;
    }
}
