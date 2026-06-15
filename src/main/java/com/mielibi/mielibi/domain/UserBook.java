package com.mielibi.mielibi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="user_book")
@ToString
@NoArgsConstructor
public class UserBook implements Serializable {

    private static final long serialVersionUID = -947585423656694361L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userBookId;

    @Column
    private Long userId;

    @Column
    private Long bookId;

    @Builder
    public UserBook(Long userBookId, Long userId, Long bookId){
        this.userBookId = userBookId;
        this.userId = userId;
        this.bookId = bookId;
    }
}
