package demo.lab.exception;

import org.springframework.http.HttpStatus;

public class GenericRuntimeException extends RuntimeException {

    public GenericRuntimeException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public GenericRuntimeException(HttpStatus httpStatus, String errorMessage) {
        super();
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus httpStatus;
    public String errorMessage;
}
