package com.service.user.service;

import com.service.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDto getUserDetails(String userId) {
        return null;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto updateUserNames(String userId, String name) {
        return null;
    }

    @Override
    public UserDto updateUserEmails(String userId, String email) {
        return null;
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {

    }

    @Override
    public UserDto changeUserRole(String userId, String newRole) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserDto restoreUser(String userId) {
        return null;
    }

    @Override
    public List<UserDto> searchUsersByName(String name) {
        return List.of();
    }

    @Override
    public List<UserDto> searchUsersByEmail(String email) {
        return List.of();
    }

    @Override
    public List<UserDto> searchUsersByRole(String role) {
        return List.of();
    }

    @Override
    public List<UserDto> filterUsersByStatus(Boolean isActive) {
        return List.of();
    }

    @Override
    public List<UserDto> filterUsersByEmailVerification(Boolean emailVerified) {
        return List.of();
    }

    @Override
    public List<UserDto> filterUsersByCreationDate(LocalDateTime startDate, LocalDateTime endDate) {
        return List.of();
    }

    @Override
    public List<UserDto> sortUsersByCreationDateAsc() {
        return List.of();
    }

    @Override
    public List<UserDto> sortUsersByLastNameDesc() {
        return List.of();
    }

    @Override
    public List<UserDto> sortUsersByRole() {
        return List.of();
    }

    @Override
    public Page<UserDto> getUsersPaginated(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public Page<UserDto> getActiveUsersPaginated(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public Page<UserDto> getDeletedUsersPaginated(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return List.of();
    }

    @Override
    public List<UserDto> listOfActiveUsers() {
        return List.of();
    }

    @Override
    public List<UserDto> getAllDeletedUsers() {
        return List.of();
    }

    @Override
    public List<UserDto> getUsersByRole(String role) {
        return List.of();
    }

    @Override
    public List<UserDto> getUsersByEmail(String email) {
        return List.of();
    }
}
