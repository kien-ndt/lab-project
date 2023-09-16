package demo.lab.db.repository;

import demo.lab.db.entity.BookEntity;
import demo.lab.db.entity.DeletedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DeletedBooksRepository extends JpaRepository<DeletedBookEntity, Integer> {
}
