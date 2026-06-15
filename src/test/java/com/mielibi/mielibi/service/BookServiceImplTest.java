package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookServiceImplTest {

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    // 책 정보 수정
    @Test
    @DisplayName("책 정보 수정")
    void UpdateBook() throws IOException {
        // given
        Book book = Book.builder()
                .bookName("쿼런틴")
                .pages(200)
                .authorName("그렉 이건")
                .build();
        bookRepository.save(book);

        // when
        book.setAuthorName("그렉 일건");
        Book updateBook = bookService.updateBook(book);

        // then
        assertThat(updateBook.getAuthorName()).isEqualTo(book.getAuthorName());
    }


}