package demo.lab.api.book.aggregation.model;

import java.util.List;

public class GetTopAuthorAndCategoryResponse {

    public List<AuthorAndCount> topAuthorList;
    public List<CategoryAndCount> topCategoryList;

    public static class AuthorAndCount {
        public String author;
        public Long count;
    }

    public static class CategoryAndCount {
        public String category;
        public Long count;
    }
}
