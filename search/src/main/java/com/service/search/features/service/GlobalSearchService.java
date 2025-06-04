package com.service.search.features.service;

import com.service.search.features.dto.GlobalSearchResponseDto;

public interface GlobalSearchService {
    GlobalSearchResponseDto performGlobalSearch(String keyword);
}
