package demo.lab.api.book.favorite;

import demo.lab.db.entity.UserFavourEntity;
import demo.lab.db.repository.BooksRepository;
import demo.lab.db.repository.UserFavoursRepository;
import demo.lab.model.GenericResponse;
import demo.lab.security.authentication.UserPrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class UpdateFavouriteBookService {

    @Autowired
    private UserFavoursRepository userFavoursRepository;

    @Autowired
    private BooksRepository booksRepository;

    public GenericResponse updateFavouriteBook(Integer id, UserPrincipalDetails userPrincipalDetails) {
        booksRepository.findById(id).orElseThrow();
        UserFavourEntity userFavourEntity = userFavoursRepository.findByBookIdAndAccountId(id,
                userPrincipalDetails.accountId).orElse(null);
        if (userFavourEntity == null) {
            UserFavourEntity newUserFavourEntity = new UserFavourEntity();
            newUserFavourEntity.bookId = id;
            newUserFavourEntity.accountId = userPrincipalDetails.accountId;
            userFavoursRepository.save(newUserFavourEntity);
        } else {
            userFavoursRepository.delete(userFavourEntity);
        }
        return new GenericResponse("Cập nhật thành công");
    }

}
