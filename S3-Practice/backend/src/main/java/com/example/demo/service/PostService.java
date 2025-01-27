package com.example.demo.service;
import com.example.demo.domain.Post;
import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final S3Service s3Service;

	@Transactional
	public PostResponseDto createPost(PostRequestDto requestDto) {
		// S3에 이미지 파일을 업로드하고 접근 URL 생성
		String imageUrl = s3Service.uploadFile(requestDto.getFile());
		// 이미지 URL에서 저장된 파일명을 추출한다
    // S3에서 이미지를 삭제할 때 필요한 파일명
		String storedFileName = extractStoredFileName(imageUrl);

		Post post = buildPostEntity(requestDto, imageUrl, storedFileName);
		Post savedPost = postRepository.save(post);
		
		return buildResponseDto(savedPost);
	}

	@Transactional
	public void deletePost(Long id) {
		Post post = findPostById(id);
		// S3에서 이미지 파일을 삭제한다
		s3Service.deleteFile(post.getStoredFileName());
		postRepository.delete(post);
	}

	/**
	 * S3 이미지 URL에서 저장된 파일명을 추출한다
	 * 예: https://bucket.s3.region.amazonaws.com/posts/uuid_filename.jpg -> posts/uuid_filename.jpg
	 */
	private String extractStoredFileName(String imageUrl) {
		return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	}

	/**
	 * Post 엔티티를 생성한다
	 * 이미지 관련 정보(URL, 원본 파일명, 저장된 파일명)를 포함한다
	 */
	private Post buildPostEntity(PostRequestDto requestDto, String imageUrl, String storedFileName) {
		return Post.builder()
				.title(requestDto.getTitle())
				.content(requestDto.getContent())
				.imageUrl(imageUrl)
				.originalFileName(requestDto.getFile().getOriginalFilename())
				.storedFileName(storedFileName)
				.build();
	}

	private PostResponseDto buildResponseDto(Post post) {
		return PostResponseDto.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.createdAt(post.getCreatedAt())
				.updatedAt(post.getUpdatedAt())
				.originalFileName(post.getOriginalFileName())
				.imageUrl(post.getImageUrl())
				.build();
	}

	public List<PostResponseDto> getAllPosts() {
		return postRepository.findAll().stream()
				.map(this::buildResponseDto)
				.collect(Collectors.toList());
	}

	public PostResponseDto getPostById(Long id) {
		Post post = findPostById(id);
		return buildResponseDto(post);
	}

	private Post findPostById(Long id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다. ID: " + id));
	}
}