package com.service.music.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file_upload")
public class FileUpload {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "key")
    private String key;

    @Column(name = "source")
    private String source;

    @Column(name = "source_id")
    private String sourceId;

    @CreationTimestamp
    @Column(name = "created_on", insertable = false, updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", insertable = false, updatable = false)
    private LocalDateTime updatedOn;

    public FileUpload(String fileName, String fileType, String key, String source, String sourceId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.key = key;
        this.source = source;
        this.sourceId = sourceId;
    }
}
