package com.example.demo.controller;

import com.example.demo.dto.PostRequestDTO;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
 
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<PostResponseDTO> createPost(@ModelAttribute PostRequestDTO requestDto) throws IOException {
		return ResponseEntity.ok(postService.createPost(requestDto));
	}


	@GetMapping
	public ResponseEntity<List<PostResponseDTO>> getAllPosts() throws IOException {
		return ResponseEntity.ok(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) throws IOException {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @RequestBody PostRequestDTO requestDto) throws IOException {
		return ResponseEntity.ok(postService.updatePost(id, requestDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}
}