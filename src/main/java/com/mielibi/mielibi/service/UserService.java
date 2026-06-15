package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.User;

import java.util.List;

public interface UserService {

    // 모든 사용자 조회
    List<User> findAllUser() throws Exception;
    // ID로 사용자 조회
    User findUserById(Long userId) throws Exception;
    // 사용자 추가
    User createUser(User user) throws Exception;

    // 사용자 수정
    User updateUser(User user) throws Exception;

    // 사용자 삭제
    void deleteUser(Long userID) throws Exception;

    // 사용자 로그인
    User login(String username, String password);


    // 추가
    // username으로 사용자 조회
    // username은 unique
    User findUserByUsername(String username) throws Exception;

}
