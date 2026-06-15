package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.Book;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    // 모든 책 조회
    List<Book> findAllBooks() throws IOException;

    // ID로 책 조회
    Book findBookById(Long bookId) throws IOException;

    // 작가 이름으로 책 조회
    List<Book> findBooksByAuthorName(String authorName) throws IOException;

    // 책 정보 추가
    Book createBook(Book book) throws IOException;

    // 책 정보 수정
    Book updateBook(Book book) throws IOException;

    // 책 정보 삭제
    void deleteBook(Long bookId) throws IOException;

}
