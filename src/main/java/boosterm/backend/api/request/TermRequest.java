package boosterm.backend.api.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@JsonNaming(SnakeCaseStrategy.class)
public class TermRequest {

    private String code;

    private String name;

    private String type;

    private String description;

    private Map<String, Boolean> graphsConfig;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Boolean> getGraphsConfig() {
        return graphsConfig;
    }

}
