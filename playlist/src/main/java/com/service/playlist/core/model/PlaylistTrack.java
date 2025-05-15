package com.service.playlist.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "playlisttrack")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistTrack {

    @Id
    @Column(name = "playlisttrack_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String playlistTrackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Column(name = "music_id")
    private String musicId;

    @Column(name = "position")
    private Integer position;

    @CreationTimestamp
    @Column(name = "added_date")
    private LocalDateTime addedDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "is_active")
    private Boolean isActive = false; // false by default
}
