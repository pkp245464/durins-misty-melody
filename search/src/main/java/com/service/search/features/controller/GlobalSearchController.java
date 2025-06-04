package com.service.search.features.controller;


import com.service.search.features.dto.GlobalSearchResponseDto;
import com.service.search.features.service.GlobalSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/durin's-misty-melody/search-service")
public class GlobalSearchController {

    private final GlobalSearchService globalSearchService;


}
