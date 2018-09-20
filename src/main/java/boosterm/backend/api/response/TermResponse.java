package boosterm.backend.api.response;

import boosterm.backend.domain.Term;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class TermResponse {

    private String code;

    private String name;

    private String type;

    private String description;

    public TermResponse(Term term) {
        this.code = term.getCode();
        this.name = term.getName();
        this.type = term.getType();
        this.description = term.getDescription();
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
}
