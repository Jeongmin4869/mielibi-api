package com.mielibi.mielibi.controller;

import com.mielibi.mielibi.domain.User;
import com.mielibi.mielibi.dto.request.UserRequestDto;
import com.mielibi.mielibi.dto.response.UserResponseDto;
import com.mielibi.mielibi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() throws Exception{
        return ResponseEntity.ok(
                userService.findAllUser()
                        .stream()
                        .map(UserResponseDto::from)
                        .toList()
                );
    }

    // ID로 특정 사용자 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long user_id) throws Exception{
        User user = userService.findUserById(user_id);
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    // 사용자 추가
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userdto) throws Exception{
        User user = new User();
        user.setUsername(userdto.getUsername());
        user.setPassword(userdto.getPassword());

        User savedUser = userService.createUser(user);

        return ResponseEntity.created(URI.create("/api/user/" + savedUser.getUserId()))
                .body(UserResponseDto.from(savedUser));
    }

    // 사용자 수정
    @PutMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long user_id, @RequestBody UserRequestDto userdto) throws Exception{
        User user = userService.findUserById(user_id);
        user.setUsername(userdto.getUsername());
        user.setPassword(userdto.getPassword());
        User updateuser = userService.updateUser(user);
        return ResponseEntity.ok(UserResponseDto.from(updateuser));
    }

    // 사용자 삭제
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> delete(@PathVariable Long user_id) throws Exception{
        userService.deleteUser(user_id);
        return ResponseEntity.noContent().build();
    }

    // 사용자 로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserRequestDto userdto){
        User loginuser = userService.login(userdto.getUsername(), userdto.getPassword());
        return ResponseEntity.ok(UserResponseDto.from(loginuser));
    }

}
