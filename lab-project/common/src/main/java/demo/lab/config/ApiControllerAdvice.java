package demo.lab.config;

import demo.lab.exception.GenericRuntimeException;
import demo.lab.model.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiControllerAdvice.class);

    @ExceptionHandler({GenericRuntimeException.class})
    public ResponseEntity<GenericResponse> genericExceptionHandler(GenericRuntimeException exception) {
        GenericResponse genericResponse = new GenericResponse(exception.errorMessage);
        return ResponseEntity.status(exception.httpStatus != null ? exception.httpStatus : HttpStatus.BAD_REQUEST).body(genericResponse);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<GenericResponse> exceptionHandler(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        GenericResponse genericResponse = new GenericResponse("Có lỗi xảy ra");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponse);
    }

}
