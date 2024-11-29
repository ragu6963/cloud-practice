package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
	@GetMapping("/")
	public String hello() {
		return "Spring Boot 환경 변수를 불러와서 응답하시오.";
	}
}