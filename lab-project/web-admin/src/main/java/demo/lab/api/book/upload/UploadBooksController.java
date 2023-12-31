package demo.lab.api.book.upload;

import com.opencsv.exceptions.CsvValidationException;
import demo.lab.api.book.upload.model.UploadBooksResponse;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadBooksController {

    @Autowired
    private UploadBooksService uploadBooksService;

    @PostMapping(value = "/v1/admin/book/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadBooksResponse> uploadBooksController(@Param("file") MultipartFile file) throws IOException, FileUploadException, CsvValidationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadBooksService.uploadBooks(file));
    }

}
