package com.service.search.features.service;

import com.service.search.core.exceptions.GlobalDurinSearchServiceException;
import com.service.search.features.dto.GlobalSearchResponseDto;
import com.service.search.features.utility.GlobalSearchMicroserviceInvoker;
import com.service.search.features.utility.MusicClassifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class GlobalSearchServiceImpl implements GlobalSearchService{

    private final MusicClassifier classifier;
    private final GlobalSearchMicroserviceInvoker microserviceInvoker;


    @Override
    public GlobalSearchResponseDto performGlobalSearch(String keyword) {
        log.info("GlobalSearchServiceImpl::performGlobalSearch called with keyword: {}", keyword);

        MusicClassifier.ContentType type = classifier.classify(keyword);
        log.info("GlobalSearchServiceImpl::Keyword classified as: {}", type);

        GlobalSearchResponseDto response = new GlobalSearchResponseDto();

        boolean userSearched, musicSearched;
         musicSearched = userSearched = false;

        try {
            switch (type) {
                case USER -> {
                    response.setUsers(microserviceInvoker.searchUsersFromUserMicroservice(keyword));
                    userSearched = true;
                }
                case MUSICNAME -> {
                    response.setMusic(microserviceInvoker.searchMusicFromMusicMicroservice(keyword));
                    musicSearched = true;
                }
            }
        }
        catch (Exception e) {
            log.warn("GlobalSearchServiceImpl::Primary search failed based on classification: {}", e.getMessage());
        }

        if (!userSearched) {
            try {
                response.setUsers(microserviceInvoker.searchUsersFromUserMicroservice(keyword));
            } catch (Exception e) {
                log.warn("GlobalSearchServiceImpl::Fallback USER search failed: {}", e.getMessage());
            }
        }

        if (!musicSearched) {
            try {
                response.setMusic(microserviceInvoker.searchMusicFromMusicMicroservice(keyword));
            }
            catch (Exception e) {
                log.warn("GlobalSearchServiceImpl::Fallback MUSIC search failed: {}", e.getMessage());
            }
        }

        boolean noUsers = Objects.isNull(response.getUsers()) || response.getUsers().isEmpty();
        boolean noMusic = Objects.isNull(response.getMusic()) || response.getMusic().isEmpty();

        if (noUsers && noMusic) {
            log.error("GlobalSearchServiceImpl::No results found in either user or music service for keyword: {}", keyword);
            throw new GlobalDurinSearchServiceException("No results found in either USER or MUSIC service for keyword: " + keyword);
        }

        return response;
    }
}
