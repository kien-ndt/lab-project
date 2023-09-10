package demo.lab.db.repository;

import demo.lab.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Iterator;

public interface BooksRepository extends JpaRepository<BookEntity, Integer> {
    Iterable<BookEntity> findByUpdatedAtBefore(LocalDateTime targetTime);
}
