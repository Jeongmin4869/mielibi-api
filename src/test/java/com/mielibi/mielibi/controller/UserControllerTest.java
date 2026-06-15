package com.mielibi.mielibi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mielibi.mielibi.domain.User;
import com.mielibi.mielibi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean // 가짜 Service
    UserService userService;

    @Autowired // SpringBoot 에서 생성한 ObjectMapper를 주입
    ObjectMapper objectMapper;
    // → Spring Context에 UserService 가짜 객체를 등록
    @Test
    @DisplayName("유저 전체 조회")
    void findAll() throws Exception{
        User user = new User();
        user.setUserId(1L);

        given(userService.findAllUser()).willReturn(List.of(user));

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L));
    }

    @Test
    @DisplayName("ID로 사용자 조회")
    void findById() throws Exception{
        User user = new User();
        user.setUserId(1L);

        given(userService.findUserById(user.getUserId())).willReturn(user);

        mockMvc.perform(get("/api/user/" + user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    @DisplayName("사용자 생성")
    void create() throws Exception{
        User requset = new User();
        requset.setUsername("jeong");
        requset.setPassword("1234");

        User response = new User();
        response.setUserId(1L);
        response.setUsername("jeong");

        given(userService.createUser(any())).willReturn(response);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requset)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.username").value("jeong"))
                .andExpect(jsonPath("$.user.password").doesNotExist());


    }

    @Test
    @DisplayName("사용자 수정")
    void update() throws Exception{
        // given
        User request = new User();
        request.setUserId(1L);

        User response = new User();
        response.setUserId(1L);

        given(userService.findUserById(1L)).willReturn(request);
        given(userService.updateUser(any())).willReturn(response);

        // when & then
        mockMvc.perform(put("/api/user/"+ request.getUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new User())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L));

    }

    @Test
    @DisplayName("사용자 삭제")
    void deleteUser() throws Exception{
        mockMvc.perform(delete("/api/user/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

    @Test
    @DisplayName("로그인")
    void loginTest() throws Exception{
        User user = new User();
        user.setUserId(1L);
        given(userService.login(any(), any())).willReturn(user);

        mockMvc.perform(post("/api/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L));
    }

}