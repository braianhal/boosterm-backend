package boosterm.backend.domain;

public class Search {

    private String term;

    private String language;

    private CustomDuration timeLimit;

    public Search(String term, String language, CustomDuration timeLimit) {
        this.term = term;
        this.language = language;
        this.timeLimit = timeLimit;
    }

    public String getTerm() {
        return term;
    }

    public String getLanguage() {
        return language;
    }

    public CustomDuration getTimeLimit() {
        return timeLimit;
    }

}
