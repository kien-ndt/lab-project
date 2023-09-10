package demo.lab.api.book.upload;

import demo.lab.api.book.upload.service.UploadBooksExecuteService;
import demo.lab.exception.GenericRuntimeException;
import demo.lab.model.GenericResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class UploadBooksService {

    private static final String FIELD_NAME = "books";

    @Autowired
    private UploadBooksExecuteService uploadBooksExecuteService;

    public GenericResponse uploadBooks(HttpServletRequest request) throws IOException, FileUploadException {

        ServletFileUpload servletFileUpload = new ServletFileUpload();
        FileItemIterator itemIterator = servletFileUpload.getItemIterator(request);
        if (!itemIterator.hasNext()) {
            throw new GenericRuntimeException("Miss field");
        }

        int count = 0;
        boolean hasTrueUploadFile = false;
        while (itemIterator.hasNext() && !hasTrueUploadFile) {
            FileItemStream itemStream = itemIterator.next();
            try (InputStream itemValueStream = itemStream.openStream()) {
                if (itemStream.getName() == null || !FIELD_NAME.equals(itemStream.getFieldName())) {
                    throw new GenericRuntimeException("Wrong field");
                } else {
                    hasTrueUploadFile = true;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(itemValueStream, StandardCharsets.UTF_8));
                    String line = bufferedReader.readLine();
                    List<List<String>> elementsList = new ArrayList<>();
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] elementArr = line.split("\";\"");
                        elementArr[0] = elementArr[0].substring(1);
                        elementArr[elementArr.length - 1] = elementArr[elementArr.length - 1].substring(0, elementArr[elementArr.length - 1].length() - 1);
                        elementsList.add(Arrays.asList(elementArr));
                        if (elementsList.size() > 1000) {
                            count += uploadBooksExecuteService.saveBooks(elementsList);
                            elementsList.clear();
                        }
                    }
                    count += uploadBooksExecuteService.saveBooks(elementsList);
                    elementsList.clear();
                }
            }
        }
        return new GenericResponse("Uploaded: " + count);
    }

}
