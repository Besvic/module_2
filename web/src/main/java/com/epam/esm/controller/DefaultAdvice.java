package com.epam.esm.controller;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.CustomResponse;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<CustomResponse> handleServiceException(ServiceException e) {
        CustomResponse response = new CustomResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<CustomResponse> handleControllerException(ControllerException e) {
        CustomResponse response = new CustomResponse("404", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

   /* @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CustomResponse> handleDaoException(NoHandlerFoundException e) {
        CustomResponse response = new CustomResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }*/


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        CustomResponse response = new CustomResponse(status.toString(), "Request isn't valid", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomResponse response = new CustomResponse("Malformed JSON Request", ex.getMessage());
        return new ResponseEntity(response, status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,HttpStatus status,
                                                                      WebRequest request) {
        CustomResponse response = new CustomResponse();
        response.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        response.setErrorCode(ex.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        CustomResponse response = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*@ExceptionHandler()
    public ResponseEntity*/
}
