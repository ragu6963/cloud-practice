package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class AwsS3Config {
	@Value("${REGION}")
	String REGION;
	@Value("${ACCESS_KEY}")
	String ACCESS_KEY;
	@Value("${SECRET_KEY}")
	String SECRET_KEY;

	@Bean
	public S3Client s3Client() {
		StaticCredentialsProvider credential = StaticCredentialsProvider.create(
				AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)
		);

		return S3Client.builder()
				.region(Region.of(REGION))
				.credentialsProvider(credential)
				.build();
	}


}