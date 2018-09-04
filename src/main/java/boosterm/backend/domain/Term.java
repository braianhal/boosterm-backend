package boosterm.backend.domain;

public class Term {

    private String term;

    private String description;

    public Term(String term, String description) {
        this.term = term;
        this.description = description;
    }

    public String getTerm() {
        return term;
    }

    public String getDescription() {
        return description;
    }

}
