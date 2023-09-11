package demo.lab.api.book.list.db;

import demo.lab.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GetListBooksRepository extends JpaRepository<BookEntity, Integer> {

    @Query(value = " " +
            " select t1.title, t2.name as author, t3.label as category" +
            " from books t1" +
            " join authors t2 on t1.authorId = t2.id" +
            " join categories t3 on t1.categoryId = t3.id" +
            " order by t1.createdAt desc, t1.id desc", nativeQuery = true)
    List<GetListBooksEntity> findAllBooks();

}
