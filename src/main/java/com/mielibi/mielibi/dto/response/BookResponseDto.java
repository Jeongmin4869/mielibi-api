package com.mielibi.mielibi.dto.response;

import com.mielibi.mielibi.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private Long bookId;
    private String bookname;
    private String author;
    private int pages;

    public static BookResponseDto from(Book book){
        return BookResponseDto.builder()
                .bookId(book.getBookId())
                .bookname(book.getBookName())
                .author(book.getAuthorName())
                .pages(book.getPages() != null? book.getPages() : 0)
                .build();
    }
}
