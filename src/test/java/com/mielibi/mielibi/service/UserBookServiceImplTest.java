package com.mielibi.mielibi.service;

import com.mielibi.mielibi.repository.BookRepository;
import com.mielibi.mielibi.repository.UserBookRepository;
import com.mielibi.mielibi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
class UserBookServiceImplTest {
    // 특정 사용자 책 등록 / 조회 / 수정 / 삭제
    @Autowired
    UserBookService userBookService;

    @Autowired
    UserBookRepository userBookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("사용자 책 등록")
    void createUserBook(){

    }

    @Test
    @DisplayName("사용자 책 조회")
    void findUserBook(){

    }

    @Test
    @DisplayName("사용자 책 수정")
    public void updateUserBook(){

    }

}