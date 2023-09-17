package demo.lab.api.book.list.db;

import demo.lab.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GetListBooksRepository extends JpaRepository<BookEntity, Integer> {

    @Query(value = " " +
            " select t1.id, t1.title, t2.name as author, t3.label as category" +
            " from books t1" +
            " join authors t2 on t1.author_id = t2.id" +
            " join categories t3 on t1.category_id = t3.id" +
            " order by t1.created_at desc, t1.id desc", nativeQuery = true)
    List<GetListBooksInterface> findAllBooks();

    @Query(value = " " +
            " select t1.id, t1.title, t2.name as author, t3.label as category" +
            " from books t1" +
            " join authors t2 on t1.author_id = t2.id" +
            " join categories t3 on t1.category_id = t3.id" +
            " where t1.id in (:idList)", nativeQuery = true)
    List<GetListBooksInterface> findAllBooksByIdIn(@Param("idList") List<Integer> id);

}
