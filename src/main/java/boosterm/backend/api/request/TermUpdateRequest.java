package boosterm.backend.api.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class TermUpdateRequest {

    private String description;

    public String getDescription() {
        return description;
    }

}
