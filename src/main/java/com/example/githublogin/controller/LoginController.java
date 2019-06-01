package com.example.githublogin.controller;


import com.example.githublogin.config.GithubProperty;
import com.example.githublogin.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("login")
@Slf4j(topic = "oauth2.0 login")
public class LoginController {
    @Autowired
    private GithubProperty githubProperty;

    @Autowired
    private LoginService loginService;

    /**
     * 处理github的第三方注册
     */
    @GetMapping("github")
    public String github() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("redirect_uri", "http://localhost:8080/getGithubAuthouriztionCode");
        params.put("client_id", githubProperty.getClientId());
        params.put("scope", "user");
        return "redirect:" + githubProperty.getAuthuorizationUrl() + "?redirect_uri=http://localhost:8080/login/getGithubAuthouriztionCode&client_id=" + githubProperty.getClientId() + "&scope=user";
    }

    /**
     * 访问https://github.com/login/oauth/authorize的回调地址，从回调地址参数获取authourization_code
     *
     * @param code
     */
    @ResponseBody
    @GetMapping("getGithubAuthouriztionCode")
    public String getGithubAuthouriztionCode(@RequestParam("code") String code, HttpSession session) {
        log.info("authorization_code:{}", code);
        loginService.doGithubLogin(code, session);
        return "success";
    }


}
