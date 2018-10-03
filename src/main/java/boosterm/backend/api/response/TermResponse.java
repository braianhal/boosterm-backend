package boosterm.backend.api.response;

import boosterm.backend.domain.Term;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.util.Map;

@JsonNaming(SnakeCaseStrategy.class)
public class TermResponse {

    private String code;

    private String name;

    private String type;

    private String description;

    private LocalDateTime lastUpdated;

    private Map<String, Boolean> graphsConfig;

    public TermResponse(Term term) {
        this.code = term.getCode();
        this.name = term.getName();
        this.type = term.getType();
        this.description = term.getDescription();
        this.lastUpdated = term.getLastUpdated();
        this.graphsConfig = term.getGraphsConfig();
    }

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

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public Map<String, Boolean> getGraphsConfig() {
        return graphsConfig;
    }

}
