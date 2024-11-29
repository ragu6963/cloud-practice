package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
	@Value("${MESSAGE}")
	private String message;

	@GetMapping("/")
	public String hello() {
		return message;
	}
}