# 환경 변수(Environment Variable)

## .env

- 환경 변수를 관리하는 파일
- Spring Boot는 .env 파일을 읽기 위해 `dotenv-java` 라이브러리가 필요하다.
    ```gradle
    implementation 'io.github.cdimascio:dotenv-java:2.2.0'
    ```


## 파일 생성과 환경 변수 사용
- .env 파일 생성
  - Spring Boot 프로젝트 루트 폴더에 `.env` 파일을 생성한다.

- .env 에제
    ```text
    DB_USER=root
    DB_PASS=password
    ```

- Spring Boot 시작 시 .env 파일을 읽어온다.
  - Spring Boot의 `*Application.java` 파일에 아래 코드를 추가한다.
  ```java
  package com.example.demo;
  import io.github.cdimascio.dotenv.Dotenv;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class DemoApplication {
      public static void main(String[] args) {
          // .env 파일 로드
          Dotenv dotenv = Dotenv.load();
          // 환경 변수 등록
          dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
  
          SpringApplication.run(DemoApplication.class, args);
      }
  }
  ```
- 설정 파일 `application.properties`에서 환경 변수를 참조한다.
    ```properties
    spring.datasource.username=${DB_USER}
    spring.datasource.password=${DB_PASS}
    ```

- 클래스 내부에서 아래와 같이 변수의 값을 불러서 사용할 수 있다.
  ```java
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
  ```
