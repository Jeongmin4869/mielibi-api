package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.domain.BookLogs;
import com.mielibi.mielibi.domain.User;
import com.mielibi.mielibi.domain.UserBook;
import com.mielibi.mielibi.repository.BookRepository;
import com.mielibi.mielibi.repository.UserBookRepository;
import com.mielibi.mielibi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookLogsServiceImplTest {

    @Autowired
    BookLogsService bookLogsService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserBookRepository userBookRepository;

    // 공통 저장 함수
    private BookLogs saveBookLog(Long userBookId, String startDt, String endDt){
        BookLogs bookLogs = BookLogs.builder()
                .userBookId(userBookId)
                .memo("memo")
                .startDt(startDt)
                .endDt(endDt)
                .nowPages(100)
                .build();
        return bookLogs;
    }

    private User saveUser(String name){
        return userRepository.save(
                User.builder().username(name).build()
        );
    }

    private Book saveBook(String title){
        return bookRepository.save(
                Book.builder().bookName(title).build()
        );
    }

    private UserBook saveUserBook(User user, Book book){
        return userBookRepository.save(
                UserBook.builder().bookId(book.getBookId()).userId(user.getUserId()).build()
        );
    }

    // 유저의 새로운 독서 시작
    @Test
    @DisplayName("독서를 시작하면 BookLog가 생성")
    void startReading(){
        //given
        User user = saveUser("김코난");
        Book book = saveBook("쿼런틴");
        UserBook userBook = saveUserBook(user, book);

        //when
        String date = "20251200";
        BookLogs log = bookLogsService.startReading(userBook.getUserBookId(),date);

        //then
        assertThat(log.getStartDt()).isEqualTo(date);
        assertThat(log.getEndDt()).isNull();
        assertThat(log.getNowPages()).isEqualTo(0);
    }

    // 유저 독서기록 업데이트하기
    @Test
    @DisplayName("현재 읽던 페이지를 업데이트")
     void updatePage(){
        //given
        User user = saveUser("김코난");
        Book book = saveBook("쿼런틴");
        UserBook userBook = saveUserBook(user, book);
        String date = "20251200";
        bookLogsService.startReading(userBook.getUserBookId(),date);

        //when
        BookLogs log = bookLogsService.updateProgress(userBook.getUserBookId(), 100);

        //then
        assertThat(log.getNowPages()).isEqualTo(100);
    }

    // 유저의 독서 종료
    @Test
    @DisplayName("독서를 완료하면 종료일자가 업데이트된다")
    void completeReading(){
        // given
        User user = saveUser("김코난");
        Book book = saveBook("쿼런틴");
        UserBook userBook = saveUserBook(user, book);
        String date = "20251200";
        bookLogsService.startReading(userBook.getUserBookId(),date);

        //when
        String endDate = "20251230";
        BookLogs log = bookLogsService.completeReading(userBook.getUserBookId(), endDate);

        //then
        assertThat(log.getEndDt()).isEqualTo(endDate);
    }

    @Test
    @DisplayName("특정 유저의 모든 독서로그를 조회한다")
    void getLogsByUser(){
        //given
        User user = saveUser("김코난");
        Book book1 = saveBook("쿼런틴");
        Book book2 = saveBook("프로젝트 헤일메리");

        UserBook userBook1 = saveUserBook(user, book1);
        UserBook userBook2 = saveUserBook(user, book2);

        String date = "20251200";
        bookLogsService.startReading(userBook1.getUserBookId(), date);
        bookLogsService.startReading(userBook2.getUserBookId(), date);

        //when
        List<BookLogs> list = bookLogsService.getLogsByUser(user.getUserId());

        //then
        assertThat(list).hasSize(2);
    }

    // 특정 유저의 가장 최근 시작 도서 가져오기
    @Test
    @DisplayName("특정 유저의 가장 최근 시작 독서 1권 조회")
    void getRecentStartBook(){
        //given
        User user = saveUser("김코난");
        Book book1 = saveBook("쿼런틴");
        Book book2 = saveBook("프로젝트 헤일메리");

        UserBook userBook1 = saveUserBook(user, book1);
        UserBook userBook2 = saveUserBook(user, book2);

        bookLogsService.startReading(userBook1.getUserBookId(), "20251100");
        bookLogsService.startReading(userBook2.getUserBookId(), "20251200");

        //when
        List<BookLogs> list = bookLogsService.getRecentStartedBooks(user.getUserId());

        //then
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getStartDt()).isEqualTo("20251200");
    }

    // 특정 유저의 가장 최근 완독 도서 가져오기
    @Test
    @DisplayName("툭정 유저의 가장 최근 완독 도서 1권 조히")
    void getRecentCompleteBook(){
        //given
        User user = saveUser("김코난");
        Book book1 = saveBook("쿼런틴");
        Book book2 = saveBook("프로젝트 헤일메리");

        UserBook userBook1 = saveUserBook(user, book1);
        UserBook userBook2 = saveUserBook(user, book2);

        bookLogsService.startReading(userBook1.getUserBookId(), "20251100");
        bookLogsService.startReading(userBook2.getUserBookId(), "20251100");

        bookLogsService.completeReading(userBook1.getUserBookId(), "20251201");
        bookLogsService.completeReading(userBook2.getUserBookId(), "20251230");

        //when
        List<BookLogs> list = bookLogsService.getRecentCompletedBooks(user.getUserId());

        //then
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getEndDt()).isEqualTo("20251230");
    }


}