package boosterm.backend.api.exception;

import boosterm.backend.domain.exception.EmptySentimentListException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class EmptySentimentListExceptionResponse extends RuntimeException{

    public EmptySentimentListExceptionResponse(EmptySentimentListException e) {
        super(e);
    }

}
