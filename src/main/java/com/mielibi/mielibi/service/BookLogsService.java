package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.BookLogs;

import java.util.List;

public interface BookLogsService {

    /* ===============================
     * 독서 상태 변경 (행동)
     * =============================== */

    // 독서 시작
    BookLogs startReading(Long userBookId, String startDt);

    // 독서 진행 (페이지 업데이트)
    BookLogs updateProgress(Long userBookId, int nowPages);

    // 독서 완료
    BookLogs completeReading(Long userBookId, String endDt);


    /* ===============================
     * 조회 (읽기 전용)
     * =============================== */

    // 모든 책 이용정보 조회
    List<BookLogs> getAllLogs();

    // 특정 사용자의 모든 독서 로그
    List<BookLogs> getLogsByUser(Long userId);

    // 최근 완독 도서
    List<BookLogs> getRecentCompletedBooks(Long userId);

    // 최근 시작 도서
    List<BookLogs> getRecentStartedBooks(Long userId);

    BookLogs findById(Long bookLogsId);

    // 책 이용정보 수정
    BookLogs updateByBookLogs(BookLogs bookLogs);

    // 책 이용정보 삭제
    void deleteBookLogs(Long bookLogsId);

}
