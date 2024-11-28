package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {
	private Long id;
	private String title;
	private String content;
	private String imageUrl;
	private String originalFileName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}