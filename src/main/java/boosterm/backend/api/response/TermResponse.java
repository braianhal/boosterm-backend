package boosterm.backend.api.response;

import boosterm.backend.domain.Term;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class TermResponse {

    private String term;

    private String description;

    public TermResponse(Term term) {
        this.term = term.getTerm();
        this.description = term.getDescription();
    }

    public String getTerm() {
        return term;
    }

    public String getDescription() {
        return description;
    }

}
