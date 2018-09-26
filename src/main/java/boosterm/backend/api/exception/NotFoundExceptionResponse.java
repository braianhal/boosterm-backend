package boosterm.backend.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotFoundExceptionResponse extends RuntimeException {

    public NotFoundExceptionResponse(String message) {
        super(message);
    }

}
