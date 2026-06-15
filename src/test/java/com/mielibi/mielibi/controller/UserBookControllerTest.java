package com.mielibi.mielibi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.domain.UserBook;
import com.mielibi.mielibi.service.UserBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserBookController.class)
public class UserBookControllerTest {
    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @MockitoBean UserBookService userBookService;

    @Test
    @DisplayName("특정 사용자의 모든 책 조회")
    void findAllUserBook() throws Exception {
        Book book = new Book();
        book.setBookId(1L);
        given(userBookService.findByUserId(any())).willReturn(List.of(book));

        mockMvc.perform(get("/api/user_book/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1L));
    }

    @Test
    @DisplayName("특정 사용자의 특정 책 조회")
    void findBookByUserId() throws Exception{
        UserBook userBook = new UserBook();
        userBook.setUserBookId(1L);
        userBook.setUserId(1L);
        userBook.setBookId(10L);
        given(userBookService.findById(1L)).willReturn(userBook);

        mockMvc.perform(get("/api/user_book/" + userBook.getUserBookId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userBookId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.bookId").value(10L));
    }

    @Test
    @DisplayName("특정 사용자의 소유 책 추가")
    void createUserBook() throws Exception{
        UserBook userBook = new UserBook();
        userBook.setUserBookId(1L);
        userBook.setUserId(1L);
        userBook.setBookId(10L);
        given(userBookService.createUserBook(any())).willReturn(userBook);

        mockMvc.perform(post("/api/user_book")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userBook)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userBookId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.bookId").value(10L));
    }

    @Test
    @DisplayName("특정 사용자의 책 정보 수정")
    void updateUserBook() throws Exception{
        UserBook request = new UserBook();
        request.setUserBookId(1L);
        request.setUserId(1L);
        request.setBookId(10L);

        UserBook response = new UserBook();
        response.setUserBookId(1L);
        response.setUserId(2L);
        response.setBookId(20L);

        given(userBookService.findById(1L)).willReturn(request);
        given(userBookService.updateUserBook(any())).willReturn(response);

        mockMvc.perform(put("/api/user_book/" + request.getUserBookId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userBookId").value(1L))
                .andExpect(jsonPath("$.userId").value(2L))
                .andExpect(jsonPath("$.bookId").value(20L));
    }

    @Test
    @DisplayName("특정 사용자의 책 정보 삭제")
    void deleteUserBook() throws Exception{
        mockMvc.perform(delete("/api/user_book/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(userBookService).deleteUserBook(1L);
    }
}