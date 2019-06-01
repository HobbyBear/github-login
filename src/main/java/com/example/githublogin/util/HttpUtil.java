package com.example.githublogin.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 发送http请求工具
 *
 * @author xch
 * @since 2019/4/11 10:11
 **/
public class HttpUtil {

    private static RestTemplate restTemplate = new RestTemplate();


    /**
     * 表单post提交
     *
     * @param url          发送url
     * @param paramMap     参数列表
     * @param responseType 返回类型
     * @return
     */
    public static ResponseEntity sendForm(String url, Map<String, Object> paramMap, Class responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        paramMap.keySet().forEach(key -> {
            map.add(key, paramMap.get(key));
        });
        HttpEntity request = new HttpEntity(map, headers);
        return restTemplate.postForEntity(url, request, responseType);
    }

    /**
     * 发送json 请求
     *
     * @param url      请求url
     * @param paramMap 请求参数
     * @return
     */
    public static ResponseEntity sendJson(String url, Map<String, Object> paramMap, Class responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));
        HttpEntity request = new HttpEntity(paramMap, headers);
        return restTemplate.postForEntity(url, request, responseType);
    }


    /**
     * 发送json 请求
     *
     * @param url        请求url
     * @param jsonObject 请求参数
     * @return
     */
    public static JSONObject sendJson(String url, JSONObject jsonObject) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));
        HttpEntity request = new HttpEntity(jsonObject, headers);
        return restTemplate.postForEntity(url, request, JSONObject.class).getBody();
    }

    /**
     * 发送json 请求
     *
     * @param url      请求url
     * @param paramMap 请求参数
     * @return
     */
    public static JSONObject sendJson(String url, Map<String, String> paramMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));
        HttpEntity request = new HttpEntity(paramMap, headers);
        return restTemplate.postForEntity(url, request, JSONObject.class).getBody();
    }

    /**
     * get方式提交请求
     *
     * @param url
     * @param paramMap
     * @param responseType
     * @return
     */
    public static ResponseEntity sendGet(String url, Map<String, String> paramMap, Class responseType) {
        return restTemplate.getForEntity(url, responseType, paramMap);
    }

}
