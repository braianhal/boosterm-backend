package boosterm.backend.api.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@JsonNaming(SnakeCaseStrategy.class)
public class TermUpdateRequest {

    private String description;

    private Map<String, Boolean> graphsConfig;

    public String getDescription() {
        return description;
    }

    public Map<String, Boolean> getGraphsConfig() {
        return graphsConfig;
    }

}
