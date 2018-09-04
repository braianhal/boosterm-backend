package boosterm.backend.api.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class TermRequest {

    private String term;

    private String description;

    public String getTerm() {
        return term;
    }

    public String getDescription() {
        return description;
    }

}
