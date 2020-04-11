package br.com.hugows.restwithspringboot.controller.handler;

import br.com.hugows.restwithspringboot.exception.ExceptionResponse;
import br.com.hugows.restwithspringboot.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionHandler> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(
                ExceptionResponse.builder()
                        .timestmap(new Date())
                        .message(ex.getMessage())
                        .details(request.getDescription(false))
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<ExceptionHandler> handleBadRequestExceptions(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(
                ExceptionResponse.builder()
                        .timestmap(new Date())
                        .message(ex.getMessage())
                        .details(request.getDescription(false))
                        .build());
    }
}
