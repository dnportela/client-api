package br.com.platformbuilders.consumer.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BasicException extends RuntimeException{

    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicException(String message) {
        super(message);
    }

    public BasicException(Throwable cause) {
        super(cause);
    }
}
