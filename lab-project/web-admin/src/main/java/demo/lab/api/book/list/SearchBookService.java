package demo.lab.api.book.list;

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

    public List<Integer> searchBook(String queryText) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .must(new QueryStringQueryBuilder(queryText + "*").defaultField("title"));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        List<Integer> searchBookIdList = new ArrayList<>();

        SearchHits<BookDocument> bookDocumentSearchHits = elasticsearchOperations.search(searchQuery,
                BookDocument.class, IndexCoordinates.of("books"));
        bookDocumentSearchHits.stream().forEach(bookDocumentSearchHit -> {
            BookDocument bookDocument = bookDocumentSearchHit.getContent();
            searchBookIdList.add(bookDocument.id);
        });
        return searchBookIdList;
    }

}
