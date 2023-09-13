package demo.lab.api.book.list.model;

import demo.lab.api.book.list.db.GetListBooksInterface;

import java.util.List;

public class GetListBooksResponse {
    public List<BookResponseEntity> bookList;

    public static class BookResponseEntity {
        public Integer id;
        public String title;
        public String author;
        public String category;
    }

}
