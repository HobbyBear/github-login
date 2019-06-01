package com.example.githublogin.service;

import com.alibaba.fastjson.JSON;
import com.example.githublogin.config.GithubProperty;
import com.example.githublogin.dao.UserInfoDao;
import com.example.githublogin.domain.UserInfo;
import com.example.githublogin.domain.UserInfoOfGithub;
import com.example.githublogin.util.HttpUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Service
@Slf4j(topic = "oauth2.0 login")
@Transactional
public class LoginService {

    @Autowired
    private GithubProperty githubProperty;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 用github的账号信息执行登陆操作
     *
     * @param authuorizationCode
     */
    public void doGithubLogin(String authuorizationCode, HttpSession session) {
        String userInfoGithubStr = "";
        //获取accessoken
        String accessToken = null;
        try {
            Map<String, Object> map = Maps.newHashMap();
            map.put("client_id", githubProperty.getClientId());
            map.put("client_secret", githubProperty.getClientSecret());
            map.put("code", authuorizationCode);
            String str = HttpUtil.sendForm(githubProperty.getGithubAccessTokenUrl(), map, String.class).getBody().toString();
            accessToken = org.apache.commons.lang3.StringUtils.substringBetween(str, "access_token=", "&");
            log.info("access_token: {}", accessToken);
        } catch (Exception e) {
            log.error("获取access_token 失败", e);
        }

        try {
            //获取github用户信息
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "token " + accessToken);
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            userInfoGithubStr = JSON.toJSONString(restTemplate.exchange(githubProperty.getGithubUserInfoUrl(), HttpMethod.GET, httpEntity, Object.class).getBody());
            log.info("github账号信息：{}", userInfoGithubStr);
        } catch (Exception e) {
            log.error("获取用户github信息失败 失败", e);
        }
        UserInfoOfGithub userInfoOfGithub = JSON.parseObject(userInfoGithubStr, UserInfoOfGithub.class);
        UserInfo userInfo = userInfoDao.findByGithubId(userInfoOfGithub.getId());
        if (userInfo == null){
            userInfo = new UserInfo();
            userInfo.setUsername(userInfoOfGithub.getLogin());
            userInfo.setAvatarUrl(userInfoOfGithub.getAvatar_url());
            userInfo.setGithubId(userInfoOfGithub.getId());
            userInfoDao.save(userInfo);
        }
        //标记当前用户登陆
        session.setAttribute("CURRENT_USER_LOGIN_STATE",true);
    }


}
