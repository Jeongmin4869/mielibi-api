package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.domain.User;
import com.mielibi.mielibi.domain.UserBook;

import java.util.List;

public interface UserBookService {
    // 특정 사용자의 모든 책 조회
    List<Book> findByUserId(Long userId) throws Exception;

    // 특정 사용자의 특정 책 조회
    UserBook findById(Long userBookId)  throws  Exception;

    // 특정 사용자의 소유 책 추가
    UserBook createUserBook(UserBook userBook)  throws  Exception;

    // 특정 사용자의 책 정보 수정
    UserBook updateUserBook(UserBook userBook)  throws  Exception;

    // 특정 사용자의 책 정보 삭제
    void deleteUserBook(Long userBookId) throws  Exception;
}
