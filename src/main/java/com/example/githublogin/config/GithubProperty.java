package com.example.githublogin.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "github")
public class GithubProperty {

    private String githubUserInfoUrl;

    private String githubAccessTokenUrl;

    private String clientId;

    private String clientSecret;

    private String authuorizationUrl;
}
