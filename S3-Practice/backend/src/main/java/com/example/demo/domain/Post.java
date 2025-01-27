package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	/**
	 * S3에 업로드된 이미지의 URL
	 * 이미지 표시에 사용됨
	 */
	@Column(nullable = true)
	private String imageUrl;

	/**
	 * 사용자가 업로드한 원본 이미지 파일명
	 */
	@Column(nullable = true)
	private String originalFileName;

	/**
	 * S3에 저장된 이미지 파일명
	 * UUID가 포함된 고유한 파일명으로 S3에서 파일 삭제 시 사용됨
	 */
	@Column(nullable = true)
	private String storedFileName;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}