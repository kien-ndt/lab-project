package demo.lab.db.repository;

import demo.lab.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<BookEntity, Integer> {
    Iterable<BookEntity> findByUpdatedAtBetween(LocalDateTime timeStart, LocalDateTime timeEnd);
    Iterable<BookEntity> findByUpdatedAtBefore(LocalDateTime now);

    @Query(value = "" +
            " select * from books where id = :id for update", nativeQuery = true)
    Optional<BookEntity> findByIdForUpdate(@Param("id") Integer id);

    boolean existsByAuthorId(Integer authorId);
    boolean existsByCategoryId(Integer categoryId);

}
