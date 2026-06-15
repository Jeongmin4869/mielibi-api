package com.mielibi.mielibi.repository;

        import com.mielibi.mielibi.domain.Book;
        import org.springframework.data.jpa.repository.JpaRepository;

        import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthorName(String authorName);
}
