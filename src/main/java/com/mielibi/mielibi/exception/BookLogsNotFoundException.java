package com.mielibi.mielibi.exception;

public class BookLogsNotFoundException extends RuntimeException{
    public BookLogsNotFoundException(Long bookLogId){
        super("존재하지 않는 독서 로그입니다. bookLogId : " + bookLogId);
    }
}
