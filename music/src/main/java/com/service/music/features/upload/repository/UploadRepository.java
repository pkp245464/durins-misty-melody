package com.service.music.features.upload.repository;

import com.service.music.core.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadRepository extends JpaRepository<FileUpload, String> {
    Optional<FileUpload> findTopBySourceAndSourceId(String source, String sourceId);
}
