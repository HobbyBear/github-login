package com.example.githublogin.dao;

import com.example.githublogin.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoDao extends JpaRepository<UserInfo,Integer> {

    UserInfo findByGithubId(Integer githubId);
}
