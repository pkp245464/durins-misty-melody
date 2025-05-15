package com.service.playlist.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @Column(name = "playlist_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String playlistId;

    @Column(name = "playlist_name")
    private String playlistName;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "playlist_description")
    private String playlistDescription;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "visibility")
    private Boolean visibility = true; // private by default

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "is_active")
    private Boolean isActive = false; // false by default

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistTrack> tracks;
}
