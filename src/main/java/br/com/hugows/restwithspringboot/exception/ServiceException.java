package br.com.hugows.restwithspringboot.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private int code;
    private String message;
    private Object[] args;

    public ServiceException(Throwable cause, int code, String message, Object... args) {
        super(cause);
        this.code = code;
        this.message = message;
        this.args = args;
    }

    public static ServiceExceptionBuilder builder() {
        return new ServiceExceptionBuilder();
    }

    @EqualsAndHashCode
    public static class ServiceExceptionBuilder {
        private Throwable cause;
        private int code;
        private String message;
        private Object[] args;

        private ServiceExceptionBuilder() {
        }

        public ServiceExceptionBuilder code(int code) {
            this.code = code;
            return this;
        }

        public ServiceExceptionBuilder message(String messageKey) {
            this.message = messageKey;
            return this;
        }

        public ServiceExceptionBuilder args(Object... args) {
            this.args = args;
            return this;
        }

        public ServiceExceptionBuilder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }


        public ServiceException build() {
            return new ServiceException(cause, code, message, args);
        }
    }

}
