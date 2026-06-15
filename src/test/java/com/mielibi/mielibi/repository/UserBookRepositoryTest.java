package com.mielibi.mielibi.repository;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.domain.UserBook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserBookRepositoryTest {

    @Autowired
    UserBookRepository userBookRepository;

    @Autowired
    BookRepository bookRepository;



    @Autowired
    TestEntityManager entityManager;

    public UserBook getSaved(){
        UserBook userBook = UserBook.builder()
                .userId(1L)
                .bookId(100L)
                .build();

        return entityManager.persist(userBook);
    }

    @Test
    @DisplayName("특정 사용자의 모든 책 조회")
    public void getAllBookInfoByUserId(){
        //given
        userBookRepository.save(UserBook.builder().userId(1L).bookId(100L).build());
        userBookRepository.save(UserBook.builder().userId(1L).bookId(200L).build());
        userBookRepository.save(UserBook.builder().userId(1L).bookId(300L).build());

        //when
        List<Book> books =  userBookRepository.findByUserId(1L);

        //then
        assertThat(books).hasSize(3)
                .extracting(Book::getBookId).containsExactlyInAnyOrder(
                        100L, 200L, 300L
                );
    }

    @Test
    @DisplayName("사용자의 책 이용정보 추가")
    public void addUserBookInfo(){
        //given
        UserBook newUserBook = (UserBook.builder().userId(1L).bookId(100L).build());

        //when
        UserBook saved = userBookRepository.save(newUserBook);

        //then
        assertThat(saved.getUserId()).isEqualTo(newUserBook.getUserId());
        assertThat(saved.getBookId()).isEqualTo(newUserBook.getBookId());
    }

    @Test
    @DisplayName("사용자의 책 이용정보 삭제")
    public void deleteUserBookInfo(){
        //given
        UserBook userBook = getSaved();
        Long id = userBook.getUserBookId();

        //when
        userBookRepository.deleteById(id);

        //then
        Optional<UserBook> deleted = userBookRepository.findById(id);
        assertThat(deleted).isEmpty();
    }


}