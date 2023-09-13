package demo.lab.api.book.search.model;

import java.util.List;

public class SearchBookResponse {
    public List<SearchBookEntity> bookList;

    public static class SearchBookEntity {
        public String title;
        public String author;
        public String category;
    }

}
