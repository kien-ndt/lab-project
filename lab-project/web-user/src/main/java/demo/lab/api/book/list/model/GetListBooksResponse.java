package demo.lab.api.book.list.model;

import java.util.List;

public class GetListBooksResponse {
    public List<BookResponseEntity> bookList;
    public Boolean isLogin;

    public static class BookResponseEntity {
        public Integer id;
        public String title;
        public String author;
        public String category;
        public Boolean isFavourite;
    }

}
