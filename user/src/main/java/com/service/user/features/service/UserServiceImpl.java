package com.service.user.features.service;

import com.password4j.Password;
import com.service.user.core.exceptions.GlobalDurinUserServiceException;
import com.service.user.core.model.UserModel;
import com.service.user.features.dto.UserDto;
import com.service.user.features.repository.UserRepository;
import com.service.user.features.utility.UserServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserDetails(String userId) {
        log.info("UserServiceImpl::getUserDetails called with input: {}", userId);
        UserModel userModel = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(()-> new GlobalDurinUserServiceException("USER-SERVICE: User with ID: " + userId + " does not exist or has been deleted."));
        log.info("UserServiceImpl::getUserDetails returning user DTO: {}", userModel);
        return UserServiceMapper.mapEntityToDto(userModel);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("UserServiceImpl::createUser called with input: {}", userDto);

        if (Objects.isNull(userDto) || Objects.isNull(userDto.getEmail()) || userDto.getEmail().isBlank()) {
            log.error("UserServiceImpl::createUser failed - Email is null or blank.");
            throw new GlobalDurinUserServiceException("USER-SERVICE: Email must not be null or blank.");
        }

        boolean isExistByEmail = userRepository.existsByEmailAndIsDeletedFalse(userDto.getEmail());
        if (isExistByEmail) {
            log.error("UserServiceImpl::createUser failed - Email '{}' is already registered.", userDto.getEmail());
            throw new GlobalDurinUserServiceException("USER-SERVICE: Email is already registered with another account.");
        }

        UserModel userModel = UserServiceMapper.mapDtoToEntity(userDto);

        UserModel savedUser = userRepository.save(userModel);
        log.info("UserServiceImpl::createUser success - User saved with ID: {}", savedUser.getId());

        UserDto savedUserDto = UserServiceMapper.mapEntityToDto(savedUser);
        log.info("UserServiceImpl::createUser returning saved user DTO: {}", savedUserDto);
        return savedUserDto;
    }

    @Override
    public UserDto updateUserNames(String userId, String name) {
        return null;
    }

    @Override
    public UserDto updateUserEmails(String userId, String email) {
        return null;
    }

    private void validateChangePasswordInput(String userId, String oldPassword, String newPassword) {
        if (Objects.isNull(userId) || userId.isBlank()) {
            throw new GlobalDurinUserServiceException("USER-SERVICE: userId must not be null or blank.");
        }
        if (Objects.isNull(oldPassword) || oldPassword.isBlank()) {
            throw new GlobalDurinUserServiceException("USER-SERVICE: oldPassword must not be null or blank.");
        }
        if (Objects.isNull(newPassword) || newPassword.isBlank()) {
            throw new GlobalDurinUserServiceException("USER-SERVICE: newPassword must not be null or blank.");
        }
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {

        validateChangePasswordInput(userId, oldPassword, newPassword);

        UserModel userModel = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new GlobalDurinUserServiceException("USER-SERVICE: User with ID " + userId + " does not exist or has been deleted."));

        boolean oldPasswordMatches = Password.check(oldPassword, userModel.getPassword()).withArgon2();
        if(!oldPasswordMatches) {
            throw new GlobalDurinUserServiceException("USER-SERVICE: Provided oldPassword does not match the password of the user.");
        }
        String newHashedPassword = Password.hash(newPassword)
                .addRandomSalt()
                .withArgon2()
                .getResult();
        userModel.setPassword(newHashedPassword);
        userRepository.save(userModel);
        log.info("UserServiceImpl::changePassword success - Password for user with ID: {} has been changed.", userId);
    }

    @Override
    public UserDto changeUserRole(String userId, String newRole) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        UserModel userModel = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(()-> new GlobalDurinUserServiceException("USER-SERVICE: User with ID: " + userId + " does not exist or has been deleted."));
        userModel.setIsDeleted(true);
        userRepository.save(userModel);
        log.info("UserServiceImpl::deleteUser success - User with ID: {} has been deleted.", userId);
    }

    @Override
    public void restoreUser(String userId) {
        UserModel userModel = userRepository.findByIdAndIsDeletedTrue(userId)
                .orElseThrow(()-> new GlobalDurinUserServiceException("USER-SERVICE: User with ID: " + userId + " does not exist or has not been deleted."));
        userModel.setIsDeleted(false);
        userRepository.save(userModel);
        log.info("UserServiceImpl::restoreUser success - User with ID: {} has been restored.", userId);
    }

    @Override
    public List<UserDto> searchUsersByName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            log.error("UserServiceImpl::searchUsersByName failed - Search keyword cannot be null or blank.");
            throw new GlobalDurinUserServiceException("UserServiceImpl:: Search keyword cannot be null or blank.");
        }

        List<UserModel> models = userRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name);

        List<UserDto> result = models.stream()
                .filter(UserModel::getIsActive)
                .map(UserServiceMapper::mapEntityToDto)
                .collect(Collectors.toList());

        log.info("UserServiceImpl::searchUsersByName returning {} users", result.size());
        return result;
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
        if(pageNumber < 0 || pageSize < 0) {
            throw new GlobalDurinUserServiceException("USER-SERVICE: Page number and page size must be greater than zero.");
        }
        log.info("UserServiceImpl::getUsersPaginated called with input: pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt").descending());
        Page<UserModel> userModelPage = userRepository.findAllActiveUsers(pageable);

        log.info("UserServiceImpl::getUsersPaginated success - Returned {} users", userModelPage.getNumberOfElements());
        return userModelPage.map(UserServiceMapper::mapEntityToDto);
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

    // Dedicated endpoint to validate user ID existence for inter-service communication
    @Override
    public Boolean validateUserId(String userId) {
        UserModel userModel = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(()-> new GlobalDurinUserServiceException("USER-SERVICE: User with ID: " + userId + " does not exist or has not been deleted."));
        return Boolean.TRUE;
    }

    @Override
    public String getEmailByUserId(String userId) {
        log.info("UserServiceImpl::getUserEmailById called with userId: {}", userId);
        UserModel userModel = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new GlobalDurinUserServiceException("USER-SERVICE: User with ID: " + userId + " does not exist or has been deleted."));
        log.info("UserServiceImpl::getUserEmailById returning email: {}", userModel.getEmail());
        return userModel.getEmail();
    }
}
