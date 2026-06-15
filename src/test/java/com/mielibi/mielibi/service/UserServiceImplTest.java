package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.User;
import com.mielibi.mielibi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("로그인 기능 확인")
    void login(){
        // givin
        User user = User.builder()
                .username("savedUser")
                .password("12345678")
                .build();
        userRepository.save(user);

        // when
        User loginUser = userService.login(user.getUsername(), user.getPassword());

        // then
        assertThat(loginUser).isNotNull();
        assertThat(loginUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(loginUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("사용자 정보 수정")
    void updateUser() throws Exception{
        // givin
        User user = User.builder()
                .username("savedUser")
                .password("12345678")
                .build();
        userRepository.save(user);

        // when
        user.setPassword("1111");
        User updatedUser = userService.updateUser(user);

        // then
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }
}