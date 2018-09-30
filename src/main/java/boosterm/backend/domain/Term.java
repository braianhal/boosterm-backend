package boosterm.backend.domain;

import java.time.LocalDateTime;

public class Term {

    private String code;

    private String name;

    private String type;

    private String description;

    private LocalDateTime lastUpdated;

    public Term(String code, String name, String type, String description, LocalDateTime lastUpdated) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.description = description;
        this.lastUpdated = lastUpdated;
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
}
