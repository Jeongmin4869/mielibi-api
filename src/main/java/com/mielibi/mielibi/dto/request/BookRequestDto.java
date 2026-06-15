package com.mielibi.mielibi.dto.request;

import com.mielibi.mielibi.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDto {
    private String bookname;
    private String author;
    private int pages;

    public Book toEntity(){
        Book book = new Book();
        book.setBookName(this.bookname);
        book.setAuthorName(this.author);
        book.setPages(this.pages);
        return book;
    }
}
