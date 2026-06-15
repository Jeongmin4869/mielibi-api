package com.mielibi.mielibi.repository;

import com.mielibi.mielibi.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    private Book getSaved(){
        Book book = Book.builder()
                .bookName("첫번째 책")
                .pages(200)
                .authorName("김작가")
                .build();

        return entityManager.persist(book);
    }

    @Test
    @DisplayName("모든 책 조회")
    void getAllBooks(){
        // given
        Book book = getSaved();
        System.out.println("======================");
        System.out.println(book.getBookId());
        System.out.println(book.getBookName());
        System.out.println(book.getPages());
        System.out.println(book.getAuthorName());
        System.out.println("======================");

        // when
        List<Book> allBooks = bookRepository.findAll();

        // then
        assertThat(allBooks).extracting(Book::getBookName)
                .containsExactlyInAnyOrder(
                        "첫번째 책"
                );
    }

    @Test
    @DisplayName("ID로 책 조회")
    void getBookById(){
        // given
        Book book = getSaved();
        System.out.println("======================");
        System.out.println(book.getBookId());
        System.out.println(book.getBookName());
        System.out.println(book.getPages());
        System.out.println(book.getAuthorName());
        System.out.println("======================");

        // when
        Long id = book.getBookId();
        Book selectBook = bookRepository.getById(id);

        // then
        assertThat(book.getBookName()).isEqualTo(selectBook.getBookName());
    }

    @Test
    @DisplayName("작가 이름으로 책 조회")
    void getBookByAuthor(){
        // given
        Book book = getSaved();
        System.out.println("======================");
        System.out.println(book.getBookId());
        System.out.println(book.getBookName());
        System.out.println(book.getPages());
        System.out.println(book.getAuthorName());
        System.out.println("======================");

        // when
        String authorName = book.getAuthorName();
        List<Book> selectedBook = bookRepository.findAllByAuthorName(authorName);

        // then
        assertThat(selectedBook).extracting(Book::getBookName)
                        .containsExactlyInAnyOrder(
                                "첫번째 책"
                        );

    }

    @Test
    @DisplayName("책 정보 추가")
    void createBook(){
        // given
        Book newbook = Book.builder()
                .bookName("새로운 책")
                .authorName("신작가")
                .pages(80)
                .build();

        // when
        Book savedbook = bookRepository.save(newbook);

        // then
        assertThat(newbook.getBookId()).isEqualTo(savedbook.getBookId());
        assertThat(newbook.getBookName()).isEqualTo(savedbook.getBookName());

    }

    @Test
    @DisplayName("책 정보 삭제")
    void deleteBook(){
        // given
        Book book = getSaved();
        System.out.println("======================");
        System.out.println(book.getBookId());
        System.out.println(book.getBookName());
        System.out.println(book.getPages());
        System.out.println(book.getAuthorName());
        System.out.println("======================");

        // when
        Long id = book.getBookId();
        bookRepository.deleteById(id);

        // then
        Optional<Book> deletedbook = bookRepository.findById(id);
        assertThat(deletedbook).isEmpty();
    }
}