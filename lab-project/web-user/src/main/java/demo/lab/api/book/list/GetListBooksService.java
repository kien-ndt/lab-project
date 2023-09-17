package demo.lab.api.book.list;

import demo.lab.api.book.list.model.GetListBooksResponse;
import demo.lab.api.book.list.model.GetListBooksResponse.BookResponseEntity;
import demo.lab.db.repository.UserFavoursRepository;
import demo.lab.elasticsearch.document.BookDocument;
import demo.lab.security.authentication.UserPrincipalDetails;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetListBooksService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private UserFavoursRepository userFavoursRepository;

    public GetListBooksResponse getListBooks(String search, UserPrincipalDetails userPrincipalDetails) {
        NativeSearchQuery searchQuery;
        if (StringUtils.hasText(search)) {
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                    .must(new QueryStringQueryBuilder(search.trim()).defaultField("title"));
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(boolQueryBuilder)
                    .build();
        } else {
            MatchAllQueryBuilder matchAllQueryBuilder = new MatchAllQueryBuilder();
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(matchAllQueryBuilder)
                    .build();
        }

        SearchHits<BookDocument> bookDocumentSearchHits = elasticsearchOperations.search(searchQuery,
                BookDocument.class, IndexCoordinates.of("books"));

        List<BookDocument> bookDocumentList =
                bookDocumentSearchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

        List<Integer> favouriteBookIdList;
        if (userPrincipalDetails != null) {
            favouriteBookIdList = userFavoursRepository.findBookIdByBookIdInListAndAccountId(
                    bookDocumentList.stream().map(bookDocument -> bookDocument.id).collect(Collectors.toList()),
                    userPrincipalDetails.accountId
            );
        } else {
            favouriteBookIdList = new ArrayList<>();
        }

        List<BookResponseEntity> bookResponseEntityList = bookDocumentList.stream().map(bookDocument -> {
            BookResponseEntity bookResponseEntity = new BookResponseEntity();
            bookResponseEntity.id = bookDocument.id;
            bookResponseEntity.title = bookDocument.title;
            bookResponseEntity.category = bookDocument.category;
            bookResponseEntity.author = bookDocument.author;
            bookResponseEntity.isFavourite = favouriteBookIdList.contains(bookDocument.id);
            return bookResponseEntity;
        }).collect(Collectors.toList());


        GetListBooksResponse getListBooksResponse = new GetListBooksResponse();
        getListBooksResponse.bookList = bookResponseEntityList;
        getListBooksResponse.isLogin = userPrincipalDetails != null;
        return getListBooksResponse;
    }

}
