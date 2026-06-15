package com.mielibi.mielibi.controller;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.dto.response.ApiResponse;
import com.mielibi.mielibi.dto.response.BookResponseDto;
import com.mielibi.mielibi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;

    // 모든 책 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> findAll() throws Exception{
        List<BookResponseDto> result = bookService.findAllBooks().stream()
                .map(BookResponseDto::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // ID로 책 조회
    @GetMapping("/{book_id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> findById(@PathVariable Long book_id) throws Exception{
        Book book = bookService.findBookById(book_id);
        return ResponseEntity.ok(ApiResponse.success(BookResponseDto.from(book)));
    }

    // 작가 이름으로 책 조회
    @GetMapping("/author/{author_name}")
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> findByAuthor(@PathVariable String author_name) throws Exception{
        List<BookResponseDto> result = bookService.findBooksByAuthorName(author_name)
                .stream()
                .map(BookResponseDto::from)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // 책 정보 추가
    @PostMapping
    public ResponseEntity<ApiResponse<BookResponseDto>> create(@RequestBody Book book) throws Exception{
        Book savedbook = bookService.createBook(book);
        URI uri = URI.create("/api/book/" + savedbook.getBookId());
        return ResponseEntity.created(uri).body(ApiResponse.success(BookResponseDto.from(savedbook)));

    }

    // 책 정보 수정
    @PutMapping("/{book_id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> update(@PathVariable Long book_id, @RequestBody Book book) throws Exception{
        Book updated = bookService.updateBook(book);
        return ResponseEntity.ok(ApiResponse.success(BookResponseDto.from(updated)));
    }

    // 책 정보 삭제
    @DeleteMapping("/{book_id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long book_id) throws Exception{
        bookService.deleteBook(book_id);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
