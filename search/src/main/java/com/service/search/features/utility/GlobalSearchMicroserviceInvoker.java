package com.service.search.features.utility;

import com.service.search.core.config.SearchUrlConfig;
import com.service.search.features.dto.MusicSearchDto;
import com.service.search.features.dto.UserSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalSearchMicroserviceInvoker {

    private final WebClient webClient;

    public List<UserSearchDto> searchUsersFromUserMicroservice(String keyword) {
        String USER_SERVICE_BASE_URL = SearchUrlConfig.USER_SERVICE_URL;

        log.info("Calling User Service to search users with keyword: {}", keyword);

        List<UserSearchDto> users = webClient.get()
                .uri(USER_SERVICE_BASE_URL + "/search?keyword={keyword}", keyword)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserSearchDto>>() {})
                .block();

        if (Objects.isNull(users) || users.isEmpty()) {
            log.error("GlobalSearchMicroserviceInvoker::searchUsersFromUserMicroservice - No users found for keyword: {}", keyword);
            throw new RuntimeException("USER-SERVICE: No users found for keyword: " + keyword);
        }
        log.info("GlobalSearchMicroserviceInvoker::searchUsersFromUserMicroservice - Found {} users for keyword: {}", users.size(), keyword);
        return users;
    }

    public List<MusicSearchDto> searchMusicFromMusicMicroservice(String keyword) {
        String MUSIC_SERVICE_BASE_URL = SearchUrlConfig.MUSIC_SERVICE_URL;

        log.info("Calling Music Service to search music with keyword: {}", keyword);

        List<MusicSearchDto> musicList = webClient.get()
                .uri(MUSIC_SERVICE_BASE_URL + "/search?keyword={keyword}", keyword)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<MusicSearchDto>>() {})
                .block();

        if (Objects.isNull(musicList) || musicList.isEmpty()) {
            log.error("No music found for keyword: {}", keyword);
            throw new RuntimeException("MUSIC-SERVICE: No music found for keyword: " + keyword);
        }

        log.info("Found {} music results for keyword: {}", musicList.size(), keyword);
        return musicList;
    }
}
