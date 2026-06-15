package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.BookLogs;
import com.mielibi.mielibi.domain.UserBook;
import com.mielibi.mielibi.repository.BookLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookLogsServiceImpl implements BookLogsService {

    @Autowired
    BookLogsRepository bookLogsRepository;

    @Override
    public BookLogs startReading(Long userBookId, String startDt) {
        BookLogs log = BookLogs.builder()
                .userBookId(userBookId)
                .startDt(startDt)
                .nowPages(0)
                .build();
        return bookLogsRepository.save(log);
    }

    @Override
    public BookLogs updateProgress(Long userBookId, int nowPages) {
        BookLogs log = bookLogsRepository
                .findTopByUserBookIdOrderByStartDtDesc(userBookId)
                .orElseThrow(() -> new IllegalStateException("독서 로그 존재하지 않음"));

        log.setNowPages(nowPages);
        return bookLogsRepository.save(log);
    }

    @Override
    public BookLogs completeReading(Long userBookId, String endDt) {
        BookLogs log = bookLogsRepository
                .findTopByUserBookIdOrderByStartDtDesc(userBookId)
                .orElseThrow(() -> new IllegalStateException("독서 로그 존재하지 않음"));

        log.setEndDt(endDt);
        return bookLogsRepository.save(log);
    }

    @Override
    public List<BookLogs> getAllLogs(){ return bookLogsRepository.findAll(); }

    @Override
    public List<BookLogs> getLogsByUser(Long userId) {
        return bookLogsRepository.findByUserId(userId);
    }

    @Override
    public List<BookLogs> getRecentCompletedBooks(Long userId) {
        return bookLogsRepository.findTopCompletedByUserId(userId, PageRequest.of(0, 1));
    }

    @Override
    public List<BookLogs> getRecentStartedBooks(Long userId) {
        return bookLogsRepository.findTopRecentStartedByUserId(userId, PageRequest.of(0, 1));
    }

    @Override
    public BookLogs findById(Long bookLogsId) {
        return bookLogsRepository.findById(bookLogsId).orElseThrow(() ->
                new IllegalArgumentException("BookLosg not found"));
    }

    @Override
    public BookLogs updateByBookLogs(BookLogs bookLogs){
        BookLogs updated = findById(bookLogs.getBookLogsId());
        updated.setEndDt(bookLogs.getEndDt());
        updated.setUserBookId(bookLogs.getUserBookId());
        updated.setNowPages(bookLogs.getNowPages());
        updated.setMemo(bookLogs.getMemo());
        updated.setStartDt(bookLogs.getStartDt());
        return bookLogsRepository.save(updated);
    }

    @Override
    public void deleteBookLogs(Long userBookId){
        bookLogsRepository.deleteById(userBookId);
    }
}
