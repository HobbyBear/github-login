package com.example.githublogin.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * 数据库中本地用户信息
 */
@Entity
@Table(name = "user_info", schema = "github_demo", catalog = "")
public class UserInfo {

    private int id;
    private String username;
    private String avatarUrl;
    private Integer githubId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Basic
    @Column(name = "github_id")
    public Integer getGithubId() {
        return githubId;
    }

    public void setGithubId(Integer githubId) {
        this.githubId = githubId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id &&
                Objects.equals(username, userInfo.username) &&
                Objects.equals(avatarUrl, userInfo.avatarUrl) &&
                Objects.equals(githubId, userInfo.githubId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, avatarUrl, githubId);
    }
}
