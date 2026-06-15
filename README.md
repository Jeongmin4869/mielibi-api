  # Mielibi

  Spring Boot로 만든 독서 기록 관리 REST API 서버입니다.   


  ## Tech Stack
  - Java 17
  - Spring Boot 3.5.0
  - Spring Data JPA
  - MySQL
  - Lombok
  
  ## 프로젝트 구조
```
src
├── controller
├── service
├── repository
├── domain
├── dto
│   ├── request
│   └── response
└── exception
```


  ## API 명세
  
  ### Book
  | Method | URL | 설명 |
  |--------|-----|------|
  | GET | /api/book | 전체 책 조회 |
  | GET | /api/book/{book_id} | ID로 책 조회 |
  | GET | /api/book/author/{author_name} | 작가명으로 책 조회 |
  | POST | /api/book | 책 등록 |
  | PUT | /api/book/{book_id} | 책 수정 |
  | DELETE | /api/book/{book_id} | 책 삭제 |

  ### User
  | Method | URL | 설명 |
  |--------|-----|------|
  | GET | /api/user | 전체 유저 조회 |
  | GET | /api/user/{user_id} | ID로 유저 조회 |
  | POST | /api/user | 유저 등록 |
  | POST | /api/user/login | 로그인 |
  | PUT | /api/user/{user_id} | 유저 수정 |
  | DELETE | /api/user/{user_id} | 유저 삭제 |

  ### UserBook (유저별 책 등록)
  | Method | URL | 설명 |
  |--------|-----|------|
  | GET | /api/user_book/user/{userId} | 특정 유저의 책 목록 조회 |
  | GET | /api/user_book/{userBookId} | 특정 UserBook 조회 |
  | POST | /api/user_book | 유저에 책 등록 |
  | PUT | /api/user_book/{userBookId} | 수정 |
  | DELETE | /api/user_book/{userBookId} | 삭제 |

  ### BookLogs (독서 기록)
  | Method | URL | 설명 |
  |--------|-----|------|
  | GET | /api/books_log | 전체 독서 기록 조회 |
  | GET | /api/books_log/{bookLogsId} | 특정 기록 조회 |
  | GET | /api/books_log/user/{userId} | 유저별 기록 조회 |
  | GET | /api/books_log/recent/user/{userId} | 최근 시작 도서 조회 |
  | GET | /api/books_log/recent_end/user/{userId} | 최근 완독 도서 조회 |
  | POST | /api/books_log | 독서 시작 기록 |
  | PUT | /api/books_log/{bookLogId} | 독서 진행도 업데이트 |
  | DELETE | /api/books_log/{bookLogId} | 기록 삭제 |



