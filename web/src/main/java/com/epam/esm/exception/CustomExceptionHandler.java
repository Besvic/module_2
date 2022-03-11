package com.epam.esm.exception;

import com.epam.esm.pojo.CustomResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;

/**
 * The type Custom exception handler.
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new CustomResponse("40402", getMessageForLocale("incorrect.url")), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new CustomResponse("40002", getMessageForLocale("incorrect.request.param")), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new CustomResponse("40402", getMessageForLocale("incorrect.url")), HttpStatus.NOT_FOUND);
    }


    /**
     * Handle not found exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<CustomResponse> handleNotFoundException(Exception e) {
        CustomResponse response = new CustomResponse(HttpStatus.BAD_REQUEST.toString(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Handle service exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<CustomResponse> handleServiceException(ServiceException e) {
        CustomResponse response = new CustomResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle controller exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({ControllerException.class})
    public ResponseEntity<CustomResponse> handleControllerException(ControllerException e) {
        CustomResponse response = new CustomResponse("40402", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * No handler found exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomResponse> noHandlerFoundException(Exception ex) {
        return new ResponseEntity<>( new CustomResponse("40002", getMessageForLocale("mismatch.exception.handler") + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        CustomResponse response = new CustomResponse(status.toString(), getMessageForLocale("request.isn't.valid") , errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        CustomResponse response = new CustomResponse("40302", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomResponse response = new CustomResponse("40002", getMessageForLocale("incorrect.json") + ex.getMessage());
        return new ResponseEntity(response, status);
    }

    /**
     * Handle all exceptions response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        CustomResponse response = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
