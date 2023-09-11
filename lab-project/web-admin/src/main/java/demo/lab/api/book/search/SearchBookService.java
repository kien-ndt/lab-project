package demo.lab.api.book.search;

import demo.lab.api.book.search.model.SearchBookResponse;
import demo.lab.api.book.search.model.SearchBookResponse.SearchBookEntity;
import demo.lab.elasticsearch.document.BookDocument;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchBookService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public SearchBookResponse searchBook(String queryText) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .must(new QueryStringQueryBuilder(queryText + "*").defaultField("title"));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        List<SearchBookEntity> searchBookEntityList = new ArrayList<>();

        SearchHits<BookDocument> bookDocumentSearchHits = elasticsearchOperations.search(searchQuery,
                BookDocument.class, IndexCoordinates.of("books"));
        bookDocumentSearchHits.stream().forEach(bookDocumentSearchHit -> {
            BookDocument bookDocument = bookDocumentSearchHit.getContent();
            SearchBookEntity searchBookEntity = new SearchBookEntity();
            searchBookEntity.title = bookDocument.title;
            searchBookEntity.author = bookDocument.author;
            searchBookEntity.category = bookDocument.category;
            searchBookEntityList.add(searchBookEntity);
        });
        SearchBookResponse searchBookResponse = new SearchBookResponse();
        searchBookResponse.bookList = searchBookEntityList;
        return searchBookResponse;
    }

}
