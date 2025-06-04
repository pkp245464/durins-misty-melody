package com.service.search.features.service;

import com.service.search.features.dto.GlobalSearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class GlobalSearchServiceImpl implements GlobalSearchService{


    @Override
    public GlobalSearchResponseDto performGlobalSearch(String keyword) {
        return null;
    }
}
