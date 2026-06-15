package com.mielibi.mielibi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.domain.BookLogs;
import com.mielibi.mielibi.service.BookLogsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(BookLogsController.class)
class BookLogsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BookLogsService bookLogsService;

    @Test
    @DisplayName("모든 책 이용정보 조회")
    void findAllBookLogs() throws Exception{
        BookLogs bookLogs = new BookLogs();
        bookLogs.setBookLogsId(1L);
        given(bookLogsService.getAllLogs()).willReturn(List.of(bookLogs));

        mockMvc.perform(get("/api/books_log"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookLogsId").value(1L));
    }

    @Test
    @DisplayName("특정 책 이용정보 조회")
    void findBookLogsById() throws Exception{
        BookLogs bookLogs = new BookLogs();
        bookLogs.setBookLogsId(1L);
        given(bookLogsService.findById(1L)).willReturn(bookLogs);

        mockMvc.perform(get("/api/books_log/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookLogsId").value(1L));
    }

    @Test
    @DisplayName("특정 사용자의 책 이용정보 조회")
    void findBookLogsByUserId() throws Exception{
        BookLogs bookLogs = new BookLogs();
        bookLogs.setBookLogsId(1L);
        given(bookLogsService.getLogsByUser(1L)).willReturn(List.of(bookLogs));

        mockMvc.perform(get("/api/books_log/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookLogsId").value(1L));
    }

    @Test
    @DisplayName("특정 사용자의 최근 완독도서 조회")
    void recentCompleted() throws  Exception{
        BookLogs bookLogs = new BookLogs();
        bookLogs.setBookLogsId(1L);
        given(bookLogsService.getRecentCompletedBooks(1L)).willReturn(List.of(bookLogs));

        mockMvc.perform(get("/api/books_log/recent_end/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookLogsId").value(1L));
    }

    @Test
    @DisplayName("특정 사용자의 최근 시작도서 조회")
    void recentStarted() throws Exception{
        BookLogs bookLogs = new BookLogs();
        bookLogs.setBookLogsId(1L);
        given(bookLogsService.getRecentStartedBooks(1L)).willReturn(List.of(bookLogs));

        mockMvc.perform(get("/api/books_log/recent/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookLogsId").value(1L));
    }

    @Test
    @DisplayName("책 이용정보 추가")
    void createBookLogs() throws Exception{
        BookLogs request= new BookLogs();
        request.setUserBookId(1L);
        request.setStartDt("2026-05-05");

        BookLogs response = new BookLogs();
        response.setBookLogsId(1L);
        response.setUserBookId(1L);
        response.setStartDt("2026-05-05");

        given(bookLogsService.startReading(anyLong(), anyString())).willReturn(response);

        mockMvc.perform(post("/api/books_log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookLogsId").value(1L))
                .andExpect(jsonPath("$.userBookId").value(1L));
    }

    @Test
    @DisplayName("책 이용정보 수정")
    void updateBookLogs() throws Exception{
        BookLogs request = new BookLogs();
        request.setBookLogsId(1L);
        request.setNowPages(100);

        BookLogs response = new BookLogs();
        response.setBookLogsId(1L);
        response.setNowPages(200);

        given(bookLogsService.updateProgress(anyLong(), anyInt())).willReturn(response);

        mockMvc.perform(put("/api/books_log/" + request.getBookLogsId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookLogsId").value(1L))
                .andExpect(jsonPath("$.nowPages").value(200));
    }

    @Test
    @DisplayName("책 이용정보 삭제")
    void deleteBookLogs() throws Exception{
        mockMvc.perform(delete("/api/books_log/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(bookLogsService).deleteBookLogs(1L);
    }
}
