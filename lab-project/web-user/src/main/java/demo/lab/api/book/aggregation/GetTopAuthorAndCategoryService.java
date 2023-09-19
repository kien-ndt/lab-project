package demo.lab.api.book.aggregation;

import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import demo.lab.api.book.aggregation.model.GetTopAuthorAndCategoryResponse;
import demo.lab.api.book.aggregation.model.GetTopAuthorAndCategoryResponse.AuthorAndCount;
import demo.lab.api.book.aggregation.model.GetTopAuthorAndCategoryResponse.CategoryAndCount;
import demo.lab.elasticsearch.document.BookDocument;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetTopAuthorAndCategoryService {

    private static final String AUTHOR_AGGR = "author_aggr";
    private static final String CATEGORY_AGGR = "category_aggr";
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public GetTopAuthorAndCategoryResponse getTopAuthorAndCategory() {
        MatchAllQueryBuilder matchAllQueryBuilder = new MatchAllQueryBuilder();
        TermsAggregationBuilder termsAggregatorAuthorBuilder = new TermsAggregationBuilder(AUTHOR_AGGR).field(
                "author_keyword").size(5);
        TermsAggregationBuilder termsAggregatorCategoryBuilder = new TermsAggregationBuilder(CATEGORY_AGGR).field(
                "category_keyword").size(5);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQueryBuilder)
                .withAggregations(termsAggregatorAuthorBuilder)
                .withAggregations(termsAggregatorCategoryBuilder)
                .withPageable(PageRequest.of(0, 1))
                .build();

        SearchHits<BookDocument> bookDocumentSearchHits = elasticsearchOperations.search(searchQuery,
                BookDocument.class, IndexCoordinates.of("books"));
        Aggregations aggregations = (Aggregations) bookDocumentSearchHits.getAggregations().aggregations();
        ParsedStringTerms parsedStringTermAuthor = aggregations.get(AUTHOR_AGGR);
        List<AuthorAndCount> authorAndCountList = parsedStringTermAuthor.getBuckets().stream().map((bucket -> {
            AuthorAndCount authorAndCount = new AuthorAndCount();
            authorAndCount.author = bucket.getKeyAsString();
            authorAndCount.count = bucket.getDocCount();
            return authorAndCount;
        })).collect(Collectors.toList());

        ParsedStringTerms parsedStringTermAuthorCategory = aggregations.get(CATEGORY_AGGR);
        List<CategoryAndCount> categoryAndCountList =
                parsedStringTermAuthorCategory.getBuckets().stream().map((bucket -> {
            CategoryAndCount categoryAndCount = new CategoryAndCount();
            categoryAndCount.category = bucket.getKeyAsString();
            categoryAndCount.count = bucket.getDocCount();
            return categoryAndCount;
        })).collect(Collectors.toList());

        GetTopAuthorAndCategoryResponse response = new GetTopAuthorAndCategoryResponse();
        response.topAuthorList = authorAndCountList;
        response.topCategoryList = categoryAndCountList;
        return response;
    }

}
