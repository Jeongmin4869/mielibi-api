package com.mielibi.mielibi.exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long bookId){
        super("존재하지 않는 책입니다. bookId : " + bookId);
    }
}
