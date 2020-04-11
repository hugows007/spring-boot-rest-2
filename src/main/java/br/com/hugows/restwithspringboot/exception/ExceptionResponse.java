package br.com.hugows.restwithspringboot.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponse implements Serializable, ExceptionHandler {

    private Date timestmap;
    private String message;
    private String details;

    @Override
    public Class<? extends Throwable>[] value() {
        return new Class[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
