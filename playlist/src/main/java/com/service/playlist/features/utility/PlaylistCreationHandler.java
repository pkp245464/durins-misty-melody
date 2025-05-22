package com.service.playlist.features.utility;

import com.service.playlist.core.config.PlaylistUrlConfig;
import com.service.playlist.core.enums.NotificationPriority;
import com.service.playlist.core.exceptions.GlobalDurinPlaylistServiceException;
import com.service.playlist.features.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylistCreationHandler {

    private final WebClient webClient;

    public void validateMusicIdFromMusicMicroservice(String musicId) {
        String MUSIC_SERVICE_URL = PlaylistUrlConfig.MUSIC_SERVICE_URL;
        Boolean isUserExists = webClient.get()
                .uri(MUSIC_SERVICE_URL + "/validate-music-id/{musicId}", musicId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(isUserExists)) {
            log.error("UserServiceImpl::validateMusicIdFromMusicMicroservice failed - Music with ID: {} does not exist", musicId);
            throw new GlobalDurinPlaylistServiceException("MUSIC-SERVICE: Music with ID: " + musicId + " does not exist");
        }
    }

    public void validateUserIdFromUserMicroservice(String userId) {
        String USER_SERVICE_URL = PlaylistUrlConfig.USER_SERVICE_URL;
        Boolean isUserExists = webClient.get()
                .uri(USER_SERVICE_URL + "/validate-user-id/{userId}", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if(Boolean.FALSE.equals(isUserExists)) {
            log.error("UserServiceImpl::validateUserIdFromUserMicroservice failed - User with ID: {} does not exist", userId);
            throw new GlobalDurinPlaylistServiceException("USER-SERVICE: User with ID: " + userId + " does not exist");
        }
    }

    public String fetchUserEmailByIdFromUserMicroservices(String userId) {
        log.info("PlaylistServiceImpl::fetchUserEmailByIdFromUserMicroservices called with userId: {}", userId);
        String USER_SERVICE_URL = PlaylistUrlConfig.USER_SERVICE_URL;
        String emailId = webClient.get()
                .uri(USER_SERVICE_URL + "/userId/{userId}", userId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        if(Objects.isNull(emailId) || emailId.isEmpty()) {
            log.error("PlaylistServiceImpl::fetchUserEmailByIdFromUserMicroservices failed - Email is null or empty for userId: {}", userId);
            throw new GlobalDurinPlaylistServiceException("USER-SERVICE: Email not found for user ID: " + userId);
        }
        log.info("PlaylistServiceImpl::fetchUserEmailByIdFromUserMicroservices success - Email fetched: {}", emailId);
        return emailId;
    }

    public void dispatchPlaylistCreationNotificationMicroservices(String userId, String playlistName, Integer noOfMusicInPlaylist) {
        log.info("PlaylistServiceImpl::dispatchPlaylistCreationNotificationMicroservices started for userId: {}, playlistName: {}", userId, playlistName);

        // Fetch user email
        String userEmail = fetchUserEmailByIdFromUserMicroservices(userId);
        log.info("PlaylistServiceImpl::dispatchPlaylistCreationNotificationMicroservices - Fetched user email: {}", userEmail);

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(userId);
        notificationRequest.setEmailId(userEmail);
        notificationRequest.setSubject("🎵 Playlist Created Successfully!");
        notificationRequest.setMessage("Hey there! Your playlist \"" + playlistName + "\" has been created with " + noOfMusicInPlaylist + " tracks.");
        notificationRequest.setNotificationPriority(NotificationPriority.NORMAL);
        log.info("PlaylistServiceImpl::dispatchPlaylistCreationNotificationMicroservices - Sending notification request: {}", notificationRequest);

        Boolean isNotificationSent = webClient.post()
                .uri(PlaylistUrlConfig.NOTIFICATION_SERVICE_URL + "/ingest")
                .bodyValue(notificationRequest)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();


        if(Boolean.FALSE.equals(isNotificationSent)) {
            log.error("PlaylistServiceImpl::dispatchPlaylistCreationNotificationMicroservices failed - Notification service returned false for userId: {}", userId);
            throw new GlobalDurinPlaylistServiceException("NOTIFICATION-SERVICE: Failed to send notification for user ID: " + userId);
        }
        log.info("PlaylistServiceImpl::dispatchPlaylistCreationNotificationMicroservices completed successfully for userId: {}", userId);
    }

}
