package boosterm.backend.domain;

public class NewsSearch {
	
    private String term;

    private String language;

    public NewsSearch(String term, String language) {
        this.term = term;
        this.language = language;
    }

    public String getTerm() {
        return term;
    }

    public String getLanguage() {
        return language;
    }
}
