package com.service.music.features.controller;

import com.service.music.features.dto.MusicDto;
import com.service.music.features.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/durin's-misty-melody/music-service")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/get-music-details/{id}")
    ResponseEntity<MusicDto> getMusicDetails(@PathVariable String id) {
        MusicDto musicDto = musicService.getMusicDetailsById(id);
        log.info("MusicController::getMusicDetails returning music with ID: {}", musicDto.getId());
        return ResponseEntity.ok(musicDto);
    }

    @PostMapping("/register")
    ResponseEntity<MusicDto> registerNewMusic(@RequestBody MusicDto musicDto) {
        MusicDto savedMusicDto = musicService.addMusicDetails(musicDto);
        log.info("MusicController::registerNewMusic success - Music with ID: {} has been registered.", savedMusicDto.getId());
        return ResponseEntity.ok(savedMusicDto);
    }
}
