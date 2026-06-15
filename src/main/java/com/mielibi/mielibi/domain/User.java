package com.mielibi.mielibi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table (name="user")
@ToString
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -947585423656694361L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Builder // Builder 패턴 사용
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
