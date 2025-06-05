package com.service.search.features.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalSearchResponseDto {
    private List<UserSearchDto> users;
    private List<MusicSearchDto> music;
}
