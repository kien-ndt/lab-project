package demo.lab.api.book.search.model;

import demo.lab.api.book.list.db.GetListBooksEntity;
import demo.lab.db.entity.BookEntity;
import demo.lab.elasticsearch.document.BookDocument;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

public class SearchBookResponse {
    public List<SearchBookEntity> bookList;

    public static class SearchBookEntity {
        public String title;
        public String author;
        public String category;
    }

}
