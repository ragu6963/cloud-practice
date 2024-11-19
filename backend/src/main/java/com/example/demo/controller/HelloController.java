package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;


@RestController
public class HelloController {
    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @GetMapping("/")
    public String hello() {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("DB User: ").append(dbUser);
        sb.append("<br/>");
        sb.append("DB Password: ").append(dbPassword);
        sb.append("</p>");
        return sb.toString();
    }
}