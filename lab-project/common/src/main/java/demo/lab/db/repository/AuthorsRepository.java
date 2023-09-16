package demo.lab.db.repository;

import demo.lab.db.entity.AuthorEntity;
import demo.lab.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuthorsRepository extends JpaRepository<AuthorEntity, Integer> {

    @Query(value = "select * from authors where name in (:nameList)", nativeQuery = true)
    List<AuthorEntity> findByNameInList(@Param("nameList") List<String> nameList);

    Optional<AuthorEntity> findByName(String authorName);

}
