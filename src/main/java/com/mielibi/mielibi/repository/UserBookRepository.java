package com.mielibi.mielibi.repository;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.domain.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    List<Book> findByUserId(Long userId);

}
