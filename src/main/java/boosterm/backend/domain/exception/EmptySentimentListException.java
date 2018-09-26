package boosterm.backend.domain.exception;

public class EmptySentimentListException extends RuntimeException {

    public EmptySentimentListException() {
        super("Can't calculate sentiment for the searched term");
    }

}
