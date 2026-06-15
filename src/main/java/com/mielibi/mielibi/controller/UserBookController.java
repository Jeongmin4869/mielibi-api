package com.mielibi.mielibi.controller;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.domain.UserBook;
import com.mielibi.mielibi.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user_book")
public class UserBookController {

    @Autowired
    UserBookService userBookService;

    // 특정 사용자의 모든 책 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> findAll(@PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(userBookService.findByUserId(userId));
    }

    // 모든 사용자의 특정 책 조회
    @GetMapping("/{userBookId}")
    public ResponseEntity<UserBook> findOne(@PathVariable Long userBookId) throws Exception {
        return ResponseEntity.ok(userBookService.findById(userBookId));
    }

    // 특정 사용자의 소유 책 추가
    @PostMapping
    public ResponseEntity<UserBook> create(@RequestBody UserBook userBook) throws Exception {
        UserBook saved = userBookService.createUserBook(userBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 특정 사용재의 책 정보 수정
    @PutMapping("/{userBookId}")
    public ResponseEntity<UserBook> update(@PathVariable Long userBookId, @RequestBody UserBook userBook) throws Exception {
        UserBook updated = userBookService.findById(userBookId);
        updated.setBookId(userBook.getBookId());
        updated.setUserId(userBook.getUserId());
        return ResponseEntity.ok(userBookService.updateUserBook(updated));
    }

    // 특정 사용자의 책 정보 삭제
    @DeleteMapping("/{userBookId}")
    public ResponseEntity<Void> delete(@PathVariable Long userBookId) throws Exception {
        userBookService.deleteUserBook(userBookId);
        return ResponseEntity.noContent().build();
    }
}
