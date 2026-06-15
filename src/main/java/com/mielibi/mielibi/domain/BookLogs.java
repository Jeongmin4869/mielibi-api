package com.mielibi.mielibi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "book_logs")
@ToString
@NoArgsConstructor
public class BookLogs implements Serializable {

    private static final long serialVersionUID = -947585423656694361L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookLogsId;

    @Column
    private Long userBookId;

    @Column
    private String memo;

    @Column
    private String startDt;

    @Column
    private String endDt;

    @Column
    private int nowPages;

    @Builder
    public BookLogs(Long bookLogsId, Long userBookId, String memo, String startDt, String endDt, int nowPages){
        this.bookLogsId = bookLogsId;
        this.userBookId = userBookId;
        this.memo = memo;
        this.startDt = startDt;
        this.endDt = endDt;
        this.nowPages = nowPages;
    }

}
