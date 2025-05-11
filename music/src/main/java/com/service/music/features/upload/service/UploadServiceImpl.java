package com.service.music.features.upload.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.service.music.core.enums.FileSourceTypeEnum;
import com.service.music.core.exceptions.GlobalDurinMusicServiceException;
import com.service.music.core.model.FileUpload;
import com.service.music.features.upload.dto.PreSignedUrlResponse;
import com.service.music.features.upload.repository.UploadRepository;
import com.service.music.features.upload.utility.FileMetadataDateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 amazonS3;
    private final UploadRepository uploadRepository;

    @Override
    public PreSignedUrlResponse getPreSignedUrl(String fileName, String fileType, String source, String sourceId) {
        if(!FileSourceTypeEnum.isValidSource(source)) {
            log.info("UploadServiceImpl::getPreSignedUrl invalid source type: {}", source);
            throw new IllegalArgumentException("Invalid source type: " + source);
        }

        if(Objects.isNull(sourceId) || sourceId.trim().isEmpty()) {
            log.info("UploadServiceImpl::getPreSignedUrl source ID is required and cannot be null or empty: {}", sourceId);
            throw new IllegalArgumentException("Source ID is required and cannot be null or empty " + sourceId);
        }

        Date expiration = new Date(System.currentTimeMillis() + 3600000); //1 hour
        String key = generateFileKey(fileName, source, sourceId);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        log.info("UploadServiceImpl::getPreSignedUrl called with input: {}", generatePresignedUrlRequest);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        FileUpload fileUpload = new FileUpload(fileName, fileType, key, source, sourceId);
        uploadRepository.save(fileUpload);
        log.info("UploadServiceImpl::getPreSignedUrl success - URL generated for file with ID: {}", fileUpload.getId());
        return new PreSignedUrlResponse(fileUpload.getId(), url.toString());
    }

    @Override
    public Boolean acknowledgeClientUpload(String fileId, Long fileSize) {
        log.info("UploadServiceImpl::acknowledgeClientUpload called with input: {}", fileId);
        Optional<FileUpload> metadata = uploadRepository.findById(fileId);
        if (metadata.isPresent()) {
            metadata.get().setFileSize(fileSize);
            uploadRepository.save(metadata.get());
            log.info("UploadServiceImpl::acknowledgeClientUpload success - File with ID: {} has been acknowledged.", fileId);
            return true;
        }
        log.info("UploadServiceImpl::acknowledgeClientUpload failed - File with ID: {} does not exist.", fileId);
        return false;
    }

    @Override
    public String getUrlWithExpiryAndId(String id, Integer days) {
        log.info("UploadServiceImpl::getUrlWithExpiryAndId called with input: {}", id);
        // Calculate the expiry date by adding the provided number of days to the current date.
        Date expiryDate = FileMetadataDateUtils.addTime(days);

        // Retrieve file metadata based on the provided file ID.
        Optional<FileUpload> fileUploadOptional = uploadRepository.findById(id);

        // If no metadata is found for the given ID, throw a custom exception.
        if (fileUploadOptional.isEmpty()) {
            log.info("UploadServiceImpl::getUrlWithExpiryAndId failed - File not found for Id:");
            throw new GlobalDurinMusicServiceException("File not found for Id " + id);
        }

        // Create a presigned URL request to retrieve the file from S3 with an expiry date.
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName,fileUploadOptional.get().getKey())
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiryDate);

        // Generate the presigned URL from the request and return it as a string.
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        log.info("UploadServiceImpl::getUrlWithExpiryAndId success - URL generated for file with ID: {}", id);
        return url.toString();
    }

    @Override
    public Map<String, String> getUrlWithExpiryForMultipleFiles(String ids, Integer days) {
        log.info("UploadServiceImpl::getUrlWithExpiryForMultipleFiles called with input: {}", ids);
        Map<String, String> urls = new HashMap<>();
        List<String> fileIds = Arrays.asList(ids.split(","));

        for (String id : fileIds) {
            String url = getUrlWithExpiryAndId(id, days);
            urls.put(id, url);
        }
        log.info("UploadServiceImpl::getUrlWithExpiryForMultipleFiles success - URLs generated for {} files.", fileIds.size());
        return urls;
    }

    private String generateFileKey(String fileName, String source, String sourceId) {
        return source + "/" + sourceId + "/" + UUID.randomUUID() + "-" + formatFileName(fileName);
    }

    private String formatFileName(String fileName) {
        if (StringUtils.hasText(fileName)) {
            return fileName
                    .replace(" ", "_")
                    .replace(",", "_")
                    .replace(":", "_")
                    .replace("?", "_")
                    .replace("/", "_")
                    .replace("\\", "_")
                    .replace("<", "_")
                    .replace(">", "_")
                    .replace("|", "_")
                    .replace("%20", "_");
        }
        return fileName;
    }
}
