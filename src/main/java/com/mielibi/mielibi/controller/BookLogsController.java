package com.mielibi.mielibi.controller;

import com.mielibi.mielibi.domain.BookLogs;
import com.mielibi.mielibi.service.BookLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books_log")
public class BookLogsController {

    @Autowired
    BookLogsService bookLogsService;

    // 모든 책 이용정보 조회
    @GetMapping
    public ResponseEntity<List<BookLogs>> findAll() throws Exception{
        return ResponseEntity.ok(bookLogsService.getAllLogs());
    }

    // 특정 책 이용정보 조회
    @GetMapping("/{bookLogsId}")
    public ResponseEntity<BookLogs> findByBookLogsId(@PathVariable Long bookLogsId) throws Exception{
        return ResponseEntity.ok(bookLogsService.findById(bookLogsId));
    }

    // 특정 사용자의 책 이용정보 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookLogs>> findByUserId(@PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(bookLogsService.getLogsByUser(userId));
    }

    // 특정 사용자의 최근 완독도서 조회
    @GetMapping("/recent_end/user/{userId}")
    public ResponseEntity<List<BookLogs>> recentCompleted(@PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(bookLogsService.getRecentCompletedBooks(userId));
    }

    // 특정 사용자의 최근 시작도서 조회
    @GetMapping("/recent/user/{userId}")
    public ResponseEntity<List<BookLogs>> recentStarted(@PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(bookLogsService.getRecentStartedBooks(userId));
    }

    // 책 이용정보 추가
    @PostMapping
    public ResponseEntity<BookLogs> create(@RequestBody BookLogs bookLog) throws Exception{
        BookLogs saved = bookLogsService.startReading(bookLog.getUserBookId(), bookLog.getStartDt());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 책 이용정보 수정
    // 독서 완료
    @PutMapping("/{bookLogId}")
    public ResponseEntity<BookLogs> update(@PathVariable Long bookLogId, @RequestBody BookLogs bookLog) throws Exception {
        return ResponseEntity.ok(bookLogsService.updateProgress(bookLogId, bookLog.getNowPages()));
    }

    // 책 이용정보 삭제
    @DeleteMapping("/{bookLogId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookLogId) throws Exception{
        bookLogsService.deleteBookLogs(bookLogId);
        return ResponseEntity.noContent().build();
    }


}
