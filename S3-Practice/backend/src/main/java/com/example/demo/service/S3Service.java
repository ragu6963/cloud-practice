package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    
    // AWS S3 서비스와 상호작용하기 위한 클라이언트
    private final S3Client s3Client;

    @Value("${BUCKET_NAME}")
    private String bucketName;
    @Value("${REGION}") 
    private String region;

    private static final String FILE_PATH_PREFIX = "posts/";
    private static final String S3_URL_FORMAT = "https://%s.s3.%s.amazonaws.com/%s";

    /**
     * 파일을 S3에 업로드하고 접근 가능한 URL을 반환한다.
     */
    public String uploadFile(MultipartFile file) {
        String storedFileName = generateStoredFileName(file);
        uploadFileToS3(file, storedFileName);
        return generateS3Url(storedFileName);
    }

    /**
     * S3에서 파일을 삭제한다.
     */
    public void deleteFile(String fileName) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 실패: " + e.getMessage());
        }
    }
    
    /**
     * 파일을 S3 버킷에 업로드한다.
     * 
     * @param file 업로드할 MultipartFile 객체
     * @param storedFileName S3에 저장될 파일명
     * @throws RuntimeException 파일 업로드 실패 시 발생
     */
    private void uploadFileToS3(MultipartFile file, String storedFileName) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(storedFileName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest, 
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
        }
    }

    /**
     * S3에 저장될 파일명을 생성한다.
     * UUID를 사용하여 고유한 파일명을 생성하고, 원본 파일명을 접미사로 추가한다.
     * 
     * @param file 원본 파일명을 가져올 MultipartFile 객체
     * @return 생성된 파일명 (형식: posts/UUID_원본파일명)
     */
    private String generateStoredFileName(MultipartFile file) {
        return FILE_PATH_PREFIX + UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    /**
     * S3 객체에 접근 가능한 URL을 생성한다.
     * 
     * @param storedFileName S3에 저장된 파일명
     * @return S3 객체 URL (형식: https://버킷명.s3.리전.amazonaws.com/파일명)
     */
    private String generateS3Url(String storedFileName) {
        return String.format(S3_URL_FORMAT, bucketName, region, storedFileName);
    }
} 