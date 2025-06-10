package com.service.music.features.music.controller;

import com.service.music.features.music.dto.MusicDto;
import com.service.music.features.music.dto.MusicSearchDto;
import com.service.music.features.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/validate-music-id/{musicId}")
    ResponseEntity<Boolean> validateMusicId(@PathVariable String musicId) {
        log.info("MusicController::validateMusicId called with input: {}", musicId);
        Boolean isValid = musicService.validateMusicId(musicId);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MusicSearchDto>> searchMusic(@RequestParam("keyword") String keyword) {
        log.info("MusicController::searchMusic called with keyword: {}", keyword);
        List<MusicSearchDto> dtos = musicService.searchMusicByKeyword(keyword);
        log.info("MusicController::searchMusic returning {} results", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-file-url/{musicId}")
    public ResponseEntity<String> getFileUrl(@PathVariable String musicId) {
        log.info("MusicController::getFileUrl called for ID: {}", musicId);
        String fileUrl = musicService.getMusicFileUrlById(musicId);
        return ResponseEntity.ok(fileUrl);
    }
}
