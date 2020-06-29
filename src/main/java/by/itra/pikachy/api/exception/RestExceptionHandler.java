package by.itra.pikachy.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ApiError apiError;

    @Autowired
    public RestExceptionHandler(ApiError apiError) {
        this.apiError = apiError;
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex,
                                                     HttpHeaders headers,
                                                     WebRequest request) {
        apiError.setMessage(ex.getLocalizedMessage());
        apiError.getErrors().add(ex.getMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }
}