package br.com.platformbuilders.consumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidationException extends BasicException{

    public ValidationException(String message) {
        super(message);
    }
}
