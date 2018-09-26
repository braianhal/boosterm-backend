package boosterm.backend.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class CantRetrieveDataExceptionResponse extends RuntimeException {

    public CantRetrieveDataExceptionResponse(Throwable baseException) {
        super(baseException);
    }

}
