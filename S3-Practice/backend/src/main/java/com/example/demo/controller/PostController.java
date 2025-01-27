package com.example.demo.controller;

import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
  
	@PostMapping
	public ResponseEntity<PostResponseDto> createPost(
			// @RequestParam은 HTTP 요청 폼 데이터를 메서드의 파라미터로 입력한다.
      // 폼 데이터의 각 Key와 파라미터를 매핑한다.
			@RequestParam("title") String title,
			@RequestParam("content") String content, 
			@RequestParam("file") MultipartFile file) throws IOException {
        
		PostRequestDto requestDto = PostRequestDto.builder()
				.title(title)
				.content(content)
				.file(file)
				.build();

		return ResponseEntity.ok(postService.createPost(requestDto));
	}

	@GetMapping
	public ResponseEntity<List<PostResponseDto>> getAllPosts() throws IOException {
		return ResponseEntity.ok(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) throws IOException {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}
}