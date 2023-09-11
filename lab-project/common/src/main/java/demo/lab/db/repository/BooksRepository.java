package demo.lab.db.repository;

import demo.lab.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public interface BooksRepository extends JpaRepository<BookEntity, Integer> {
    Iterable<BookEntity> findByUpdatedAtBetween(LocalDateTime timeStart, LocalDateTime timeEnd);
    Iterable<BookEntity> findByUpdatedAtBefore(LocalDateTime now);

    List<BookEntity> findByOrderByCreatedAtDescIdDesc();
}
