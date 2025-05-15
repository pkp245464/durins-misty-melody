package com.service.user.features.controller;

import com.service.user.features.dto.UserDto;
import com.service.user.features.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/user-service")
public class UserController {

    private final UserService userService;

    @GetMapping("/user-details/{userId}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable String userId) {
        UserDto userDto = userService.getUserDetails(userId);
        log.info("UserController::getUserDetails returning user with ID: {}", userDto.getId());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {
        log.info("UserController::registerNewUser called with input: {}", userDto);
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PatchMapping("/change-password/{userId}")
    public ResponseEntity<String> changePassword( @PathVariable String userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        log.info("UserController::changePassword called for user ID: {}", userId);
        userService.changePassword(userId, oldPassword, newPassword);
        return ResponseEntity.ok("Password changed successfully for user ID: " + userId);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        log.info("UserController::deleteUser success - User with ID: {} has been deleted.", userId);
        return ResponseEntity.ok("User with ID: " + userId + " has been deleted.");
    }

    @PatchMapping("/restore/{userId}")
    public ResponseEntity<String> restoreUser(@PathVariable String userId) {
        log.info("UserController::restoreUser called for user ID: {}", userId);
        userService.restoreUser(userId);
        return ResponseEntity.ok("User with ID: " + userId + " has been restored successfully.");
    }

    @GetMapping("/records")
    public ResponseEntity<Page<UserDto>> getUsersPaginated(@RequestParam(defaultValue = "0") int pageNumber,
                                                           @RequestParam(defaultValue = "10") int pageSize) {

        log.info("UserController::getUsersPaginated called with pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        Page<UserDto> userPage = userService.getUsersPaginated(pageNumber, pageSize);
        log.info("UserController::getUsersPaginated success - Returned {} users", userPage.getNumberOfElements());
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/validate-user-id/{userId}")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId) {
        log.info("UserController::validateUser called with input: {}", userId);
        Boolean isValid = userService.validateUserId(userId);
        return ResponseEntity.ok(isValid);
    }
}