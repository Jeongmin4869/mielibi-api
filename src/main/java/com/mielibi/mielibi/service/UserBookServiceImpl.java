package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.domain.User;
import com.mielibi.mielibi.domain.UserBook;
import com.mielibi.mielibi.repository.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookServiceImpl implements UserBookService {

    @Autowired
    private UserBookRepository userBookRepository;

    @Override
    public List<Book> findByUserId(Long userId) throws Exception {
        return userBookRepository.findByUserId(userId);
    }

    @Override
    public UserBook findById(Long userBookId) throws Exception {
        return userBookRepository.findById(userBookId).orElseThrow(() ->
                new IllegalArgumentException("UserBook not found"));
    }

    @Override
    public UserBook createUserBook(UserBook userBook) throws Exception {
        return userBookRepository.save(userBook);
    }

    @Override
    public UserBook updateUserBook(UserBook userBook) throws Exception {
        UserBook exUserBook = findById(userBook.getUserBookId());
        exUserBook.setBookId(userBook.getBookId()); // 책 정보만 업데이트
        return userBookRepository.save(exUserBook);
    }

    @Override
    public void deleteUserBook(Long userBookId) throws Exception {
        userBookRepository.deleteById(userBookId);
    }
}
