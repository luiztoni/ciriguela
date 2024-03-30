package br.edu.ciriguela.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.MULTI_STATUS)
public final class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }
}
