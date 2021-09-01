package com.xdesign.munro.error;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<String> handleValidationException(ConstraintViolationException ex) {
                                                       //Remove package and class name from error message
        return new ResponseEntity<>(ex.getMessage().substring(ex.getMessage().indexOf(".") + 1), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleFailedConversion(ConversionFailedException ex) {
        String message = ex.getMessage();
        if (ex.getRootCause() != null) {
            String rootCauseMessage = ex.getRootCause().getMessage();
            if (rootCauseMessage.contains("SortDirection")) {
                message = "Sort direction should be either ASC or DESC";
            } else if (rootCauseMessage.contains("Category")) {
                message = "Category should be either MUN or TOP";
            }
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
