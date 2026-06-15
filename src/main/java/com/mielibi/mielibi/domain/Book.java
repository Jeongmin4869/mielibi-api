package com.mielibi.mielibi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="book")
@ToString
public class Book implements Serializable {

    private static final long serialVersionUID = -947585423656694361L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column
    private String bookName;

    @Column
    private String authorName;

    @Column
    private Integer pages;

    @Builder
    public Book(String bookName, String authorName, Integer pages){
        this.bookName = bookName;
        this.authorName = authorName;
        this.pages = pages;
    }

}
