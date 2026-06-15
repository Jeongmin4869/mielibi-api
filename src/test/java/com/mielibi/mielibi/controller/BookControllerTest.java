package com.mielibi.mielibi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BookService bookService;

    @Test
    @DisplayName("모튼 책 조회")
    void findAll() throws Exception {
        Book book = new Book();
        book.setBookId(1L);
        given(bookService.findAllBooks()).willReturn(List.of(book));

        mockMvc.perform(get("/api/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1L));
    }

    @Test
    @DisplayName("ID로 책 조회")
    void findById() throws Exception {
        Book book = new Book();
        book.setBookId(1L);
        given(bookService.findBookById(1L)).willReturn(book);

        mockMvc.perform(get("/api/book/" + book.getBookId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1L));
    }

    @Test
    @DisplayName("작가 이름으로 책 조회")
    void findByAuthorName() throws Exception{
        Book book = new Book();
        book.setBookId(1L);
        book.setAuthorName("jeong");
        given(bookService.findBooksByAuthorName("jeong")).willReturn(List.of(book));

        mockMvc.perform(get("/api/book/author/" + book.getAuthorName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1L))
                .andExpect(jsonPath("$[0].author").value("jeong"));
    }

    @Test
    @DisplayName("책 정보 추가")
    void create() throws Exception{
        Book book = new Book();
        book.setBookId(1L);
        book.setBookName("jeong");

        given(bookService.createBook(any())).willReturn(book);

        mockMvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(1L))
                .andExpect(jsonPath("$.bookname").value("jeong"));
    }

    @Test
    @DisplayName("책 정보 수정")
    void update() throws Exception{
        Book book = new Book();
        book.setBookId(1L);
        given(bookService.updateBook(any())).willReturn(book);

        mockMvc.perform(put("/api/book/" + book.getBookId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1L));
    }

    @Test
    @DisplayName("책 정보 삭제")
    void deleteBook() throws Exception{
        mockMvc.perform(delete("/api/book/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(bookService).deleteBook(1L);
    }
}