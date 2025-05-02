package com.service.music.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "musics")
public class MusicModel {
    @Id
    private String id;
    private String title;
    private Artist artist;
    private Stats stats;
    private String visibility;
    private List<String> tags;
    private Album album;
    private MediaDetails mediaDetails;
    private AudioMetadata audioMetadata;
    private UploadInfo uploadInfo;
}
