package demo.lab.db.repository;

import demo.lab.db.entity.AuthorEntity;
import demo.lab.db.entity.UserFavourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserFavoursRepository extends JpaRepository<UserFavourEntity, Integer> {

    Optional<UserFavourEntity> findByBookIdAndAccountId(Integer bookId, Integer accountId);

    @Query(value = "" +
            " select book_id from user_favours " +
            " where book_id in (:bookList) and account_id = :accountId",
            nativeQuery = true)
    List<Integer> findBookIdByBookIdInListAndAccountId(
            @Param("bookList") List<Integer> nameList, @Param("accountId") Integer accountId);

}
