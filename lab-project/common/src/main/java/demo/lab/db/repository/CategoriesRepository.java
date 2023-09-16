package demo.lab.db.repository;

import demo.lab.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query(value = "select * from categories where label in (:labelList)", nativeQuery = true)
    List<CategoryEntity> findByLabelInList(@Param("labelList") List<String> labelList);

    Optional<CategoryEntity> findByLabel(String categoryLabel);
}
