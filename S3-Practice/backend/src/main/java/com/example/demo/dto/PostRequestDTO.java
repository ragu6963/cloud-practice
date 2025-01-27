package com.example.demo.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class PostRequestDto {
	private String title;
	private String content;
	private MultipartFile file;
}