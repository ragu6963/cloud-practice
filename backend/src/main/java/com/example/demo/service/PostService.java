package com.example.demo.service;

import com.example.demo.dto.PostRequestDTO;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final S3Client s3Client;

	@Value("${BUCKET_NAME}")
	String BUCKET_NAME;
	@Value("${REGION}")
	String REGION;

	@Transactional
	public PostResponseDTO createPost(PostRequestDTO requestDto) {
		// S3에 이미지 업로드
		String fileName = getFileName(requestDto.getFile(), "posts");
		String imageUrl = uploadToS3(requestDto.getFile(), fileName);

		Post post = new Post();
		post.setTitle(requestDto.getTitle());
		post.setContent(requestDto.getContent());
		post.setImageUrl(imageUrl);
		post.setFileName(requestDto.getFile().getOriginalFilename());

		Post savedPost = postRepository.save(post);

		PostResponseDTO responseDto = new PostResponseDTO();
		responseDto.setId(savedPost.getId());
		responseDto.setTitle(savedPost.getTitle());
		responseDto.setContent(savedPost.getContent());
		responseDto.setImageUrl(savedPost.getImageUrl());
		responseDto.setFileName(savedPost.getFileName());

		return responseDto;
	}

	private String uploadToS3(MultipartFile file, String fileName) {

		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(BUCKET_NAME)
							.key(fileName)
							.contentType(file.getContentType())
							.build(),
					RequestBody.fromInputStream(file.getInputStream(), file.getSize())
			);
		} catch (Exception e) {
			throw new RuntimeException("Failed to upload file", e);
		}

		return "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/" + fileName;
	}

	private String getFileName(MultipartFile file, String dirName) {
		String fileName = dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
		return fileName;
	}

	@Transactional(readOnly = true)
	public List<PostResponseDTO> getAllPosts() throws IOException {
		return postRepository.findAll().stream()
				.map(post -> toResponseDTO(post))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PostResponseDTO getPostById(Long id) throws IOException {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

		return toResponseDTO(post);
	}

	@Transactional
	public PostResponseDTO updatePost(Long id, PostRequestDTO requestDto) throws IOException {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

		post.setTitle(requestDto.getTitle());
		post.setContent(requestDto.getContent());

		return toResponseDTO(post);
	}

	@Transactional
	public void deletePost(Long id) {
		if (!postRepository.existsById(id)) {
			throw new IllegalArgumentException("Post not found with id: " + id);
		}
		postRepository.deleteById(id);
	}

	private PostResponseDTO toResponseDTO(Post post) {

		return PostResponseDTO.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.createdAt(post.getCreatedAt())
				.updatedAt(post.getUpdatedAt())
				.fileName(post.getFileName())
				.imageUrl(post.getImageUrl())
				.build();

	}
}