package com.mielibi.mielibi.exception;

public class UserBookNotFoundException extends RuntimeException{
    public UserBookNotFoundException(Long userBookId){
        super("존재하지 않는 유저 책 정보입니다. userBookId : " + userBookId);
    }
}
