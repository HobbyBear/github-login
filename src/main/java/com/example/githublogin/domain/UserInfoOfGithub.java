package com.example.githublogin.domain;


import lombok.Data;

/**
 * github上的用户信息
 */
@Data
public class UserInfoOfGithub {


    /**
     * login : octocat
     * id : 1
     * node_id : MDQ6VXNlcjE=
     * avatar_url : https://github.com/images/error/octocat_happy.gif
     */

    //用户名
    private String login;
    private int id;
    private String node_id;
    //用户头像
    private String avatar_url;


}
