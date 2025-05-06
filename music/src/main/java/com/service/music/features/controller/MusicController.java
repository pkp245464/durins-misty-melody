package com.service.music.features.controller;

import com.service.music.features.dto.MusicDto;
import com.service.music.features.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/durin's-misty-melody/music-service")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @PostMapping("/register")
    ResponseEntity<MusicDto> registerNewMusic(@RequestBody MusicDto musicDto) {
        MusicDto savedMusicDto = musicService.addMusicDetails(musicDto);
        log.info("MusicController::registerNewMusic success - Music with ID: {} has been registered.", savedMusicDto.getId());
        return ResponseEntity.ok(savedMusicDto);
    }
}
