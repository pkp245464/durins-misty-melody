package com.service.music.features.music.utility;

import com.service.music.core.model.*;
import com.service.music.features.music.dto.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class MusicMapper {

    public static MusicModel mapMusicDetailsDtoToModel(MusicDto musicDto) {
        // Default values for playCount and likes
        return MusicModel.builder()
                .id(musicDto.getId())
                .title(musicDto.getTitle())
                .artist(toEntity(musicDto.getArtist()))
                .stats(new Stats(0L, 0L)) // Default values for playCount and likes
                .visibility(musicDto.getVisibility())
                .tags(musicDto.getTags())
                .album(toEntity(musicDto.getAlbum()))
                .mediaDetails(toEntity(musicDto.getMediaDetails()))
                .audioMetadata(toEntity(musicDto.getAudioMetadata()))
                .uploadInfo(toEntity(musicDto.getUploadInfo()))
                .build();
    }

    private static Artist toEntity(ArtistDto artistDto) {
        return artistDto != null ? new Artist(artistDto.getArtistId(), artistDto.getArtistName(), artistDto.getArtistCountry()) : null;
    }

    private static Album toEntity(AlbumDto albumDto) {
        return albumDto != null ? new Album(albumDto.getAlbumId(), albumDto.getAlbumName()) : null;
    }

    private static MediaDetails toEntity(MediaDetailsDto mediaDetailsDto) {
        return mediaDetailsDto != null ? new MediaDetails(mediaDetailsDto.getFileUrl(), mediaDetailsDto.getCoverImageUrl(), mediaDetailsDto.getLyrics()) : null;
    }

    private static AudioMetadata toEntity(AudioMetadataDto audioMetadataDto) {
        return audioMetadataDto != null ? new AudioMetadata(audioMetadataDto.getDuration(), audioMetadataDto.getFormat(), audioMetadataDto.getLanguage(), audioMetadataDto.getReleaseDate()) : null;
    }

    private static UploadInfo toEntity(UploadInfoDto uploadInfoDto) {
        return uploadInfoDto != null ? new UploadInfo(uploadInfoDto.getUploadedBy(), uploadInfoDto.getUploadDate()) : null;
    }

    public static MusicDto mapMusicDetailsModelToDto(MusicModel model) {
        return new MusicDto(
                model.getId(),
                model.getTitle(),
                toDto(model.getArtist()),
                toDto(model.getStats()),
                model.getVisibility(),
                model.getTags(),
                toDto(model.getAlbum()),
                toDto(model.getMediaDetails()),
                toDto(model.getAudioMetadata()),
                toDto(model.getUploadInfo())
        );
    }

    private static ArtistDto toDto(Artist entity) {
        return entity != null ? new ArtistDto(entity.getArtistId(), entity.getArtistName(), entity.getArtistCountry()) : null;
    }

    private static AlbumDto toDto(Album entity) {
        return entity != null ? new AlbumDto(entity.getAlbumId(), entity.getAlbumName()) : null;
    }

    private static MediaDetailsDto toDto(MediaDetails entity) {
        return entity != null ? new MediaDetailsDto(entity.getFileUrl(), entity.getCoverImageUrl(), entity.getLyrics()) : null;
    }

    private static AudioMetadataDto toDto(AudioMetadata entity) {
        return entity != null ? new AudioMetadataDto(entity.getDuration(), entity.getFormat(), entity.getLanguage(), entity.getReleaseDate()) : null;
    }

    private static UploadInfoDto toDto(UploadInfo entity) {
        return entity != null ? new UploadInfoDto(entity.getUploadedBy(), entity.getUploadDate()) : null;
    }

    private static StatsDto toDto(Stats stats) {
        return stats != null ? new StatsDto(stats.getPlayCount(), stats.getLikes()) : null;
    }

    public static MusicSearchDto toSearchDto(MusicModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return MusicSearchDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .artistName(model.getArtist() != null ? model.getArtist().getArtistName() : null)
                .tags(model.getTags())
                .albumName(model.getAlbum() != null ? model.getAlbum().getAlbumName() : null)
                .build();
    }

    public static MusicDetailDto toSimplifiedDto(MusicModel musicModel) {
        log.info("MusicMapper::toSimplifiedDto called with MusicModel ID: {}", musicModel != null ? musicModel.getId() : "null");
        if (Objects.isNull(musicModel)) {
            log.warn("MusicMapper::toSimplifiedDto received null MusicModel, returning null");
            return null;
        }
        MusicDetailDto musicDetailDto = getMusicDetailDto(musicModel);
        log.info("MusicMapper::toSimplifiedDto returning MusicDetailDto: {}", musicDetailDto);
        return musicDetailDto;
    }

    private static MusicDetailDto getMusicDetailDto(MusicModel musicModel) {
        MusicDetailDto musicDetailDto = new MusicDetailDto();
        musicDetailDto.setMusicId(musicModel.getId());
        musicDetailDto.setTitle(musicModel.getTitle());
        musicDetailDto.setArtistName(musicModel.getArtist() != null ? musicModel.getArtist().getArtistName() : null);
        musicDetailDto.setAlbumName(musicModel.getAlbum() != null ? musicModel.getAlbum().getAlbumName() : null);
        musicDetailDto.setTags(musicModel.getTags());
        musicDetailDto.setFileUrl(musicModel.getMediaDetails() != null ? musicModel.getMediaDetails().getFileUrl() : null);
        return musicDetailDto;
    }
}
