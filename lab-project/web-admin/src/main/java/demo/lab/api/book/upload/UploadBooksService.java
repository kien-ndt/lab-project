package demo.lab.api.book.upload;

import demo.lab.api.book.upload.model.UploadBooksRequest;
import demo.lab.api.book.upload.model.UploadBooksResponse;
import demo.lab.api.book.upload.service.UploadBooksExecuteService;
import demo.lab.exception.GenericRuntimeException;
import demo.lab.model.GenericResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UploadBooksService {

    private static final String CSV_SPLIT = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    @Autowired
    private UploadBooksExecuteService uploadBooksExecuteService;

    public UploadBooksResponse uploadBooks(MultipartFile file) throws IOException, FileUploadException {
        int count = 0;
        try(InputStream inputStream = file.getInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();
            List<List<String>> elementsList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] elementArr = line.split(CSV_SPLIT);
                elementsList.add(Arrays.asList(elementArr));
                if (elementsList.size() >= 1000) {
                    count += uploadBooksExecuteService.saveBooks(elementsList);
                    elementsList.clear();
                }
            }
            count += uploadBooksExecuteService.saveBooks(elementsList);
            elementsList.clear();
        }
        UploadBooksResponse response = new UploadBooksResponse();
        response.count = count;
        return response;
    }

}
