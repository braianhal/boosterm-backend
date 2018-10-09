package boosterm.backend.domain;

import java.time.LocalDateTime;
import java.util.Map;

import static boosterm.backend.config.SystemConfig.now;

public class Term {

    private String code;

    private String name;

    private String type;

    private String description;

    private LocalDateTime lastUpdated;

    private Map<String, Boolean> graphsConfig;

    public Term(String code, String name, String type, String description, LocalDateTime lastUpdated, Map<String, Boolean> graphsConfig) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.description = description;
        this.lastUpdated = lastUpdated;
        this.graphsConfig = graphsConfig;
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

    public Term update(String newDescription, Map<String, Boolean> newGraphsConfig, LocalDateTime now) {
        String description = newDescription == null ? this.description : newDescription;

        Map<String, Boolean> graphsConfig = this.graphsConfig;
        if (newGraphsConfig != null) {
            newGraphsConfig.forEach(graphsConfig::put);
        }

        LocalDateTime lastUpdated = (newDescription != null || newGraphsConfig != null) ? now() : this.lastUpdated;

        return new Term(this.code, this.name, this.type, description, lastUpdated, graphsConfig);
    }

}
