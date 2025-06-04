package com.service.user.features.service;

import com.service.user.features.dto.UserDto;
import com.service.user.features.dto.UserSearchResponseDto;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    // Methods for getting User, creating User, updating Usernames, updating User emails, changing User password, and changing a User role
    UserDto getUserDetails(String userId);
    UserDto createUser(UserDto userDto);
    UserDto updateUserNames(String userId, String name);
    UserDto updateUserEmails(String userId, String email);
    void changePassword(String userId, String oldPassword, String newPassword);
    UserDto changeUserRole(String userId, String newRole);
    void deleteUser(String userId);
    void restoreUser(String userId);


    // Methods for searching users by name, email, and role
    List<UserSearchResponseDto> searchUsersByName(String keyword);
    List<UserDto> searchUsersByEmail(String email);
    List<UserDto> searchUsersByRole(String role);


    // Methods for filtering users by status, email verification, and creation date range
    List<UserDto> filterUsersByStatus(Boolean isActive);
    List<UserDto> filterUsersByEmailVerification(Boolean emailVerified);
    List<UserDto> filterUsersByCreationDate(LocalDateTime startDate, LocalDateTime endDate);


    // Methods for sorting users by creation date, last name, and role
    List<UserDto> sortUsersByCreationDateAsc();
    List<UserDto> sortUsersByLastNameDesc();
    List<UserDto> sortUsersByRole();


    // Methods for paginating users, active users, and deleted users
    Page<UserDto> getUsersPaginated(int pageNumber, int pageSize);
    Page<UserDto> getActiveUsersPaginated(int pageNumber, int pageSize);
    Page<UserDto> getDeletedUsersPaginated(int pageNumber, int pageSize);

    // Methods for getting all users, active users, deleted users, and users by role or email
    List<UserDto> getAllUsers();
    List<UserDto> listOfActiveUsers();
    List<UserDto> getAllDeletedUsers();
    List<UserDto> getUsersByRole(String role);
    List<UserDto> getUsersByEmail(String email);

    // Dedicated endpoint to validate user ID existence for inter-service communication
    Boolean validateUserId(String userId);

    /**
     * Retrieves the email address associated with the given user ID.
     * This method is primarily used for inter-service communication to fetch user email information.
     *
     * @param userId the unique identifier of the user
     * @return the email address of the specified user
     */
    String getEmailByUserId(String userId);
}
