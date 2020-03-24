package br.com.hugows.restwithspringboot.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Date;

public class ExceptionResponse implements Serializable, ExceptionHandler {

    private Date timestmap;
    private String message;
    private String details;

    public ExceptionResponse(Date timestmap, String message, String details) {
        this.timestmap = timestmap;
        this.message = message;
        this.details = details;
    }

    public Date getTimestmap() {
        return timestmap;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public Class<? extends Throwable>[] value() {
        return new Class[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
