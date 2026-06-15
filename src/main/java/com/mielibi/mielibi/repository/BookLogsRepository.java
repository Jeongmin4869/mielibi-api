package com.mielibi.mielibi.repository;

import com.mielibi.mielibi.domain.BookLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookLogsRepository extends JpaRepository<BookLogs, Long> {
    Optional<BookLogs> findTopByUserBookIdOrderByStartDtDesc(Long userBookId);

    @Query("""
            select bl
            from BookLogs bl
            join UserBook ub on bl.userBookId  = ub.userBookId
            where ub.userId = :userId              
                 """)
    List<BookLogs> findByUserId(@Param("userId") Long userId);

    @Query("""
            select bl
            from BookLogs bl
            join UserBook ub on bl.userBookId = ub.userBookId
            where ub.userId = :userId 
              and bl.endDt is not null
            order by bl.endDt desc         
            """)
    List<BookLogs> findTopCompletedByUserId(@Param("userId") Long userId,  Pageable pageable);

    @Query("""
            select bl
            from BookLogs bl
            join UserBook ub on bl.userBookId = ub.userBookId
            where ub.userId = :userId
            order by bl.startDt desc 
            """)
    List<BookLogs> findTopRecentStartedByUserId(@Param("userId") Long userId,  Pageable pageable);
}
