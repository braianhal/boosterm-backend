package boosterm.backend.domain;

public class NewsSearch implements Search {
	
    private String term;

    private String language;
    
    private String from;
    
    private String to;

    public NewsSearch(String term, String language, String from, String to) {
        this.term = term;
        this.language = language;
        this.from = from;
        this.to = to;
    }

    public String getTerm() {
        return term;
    }

    public String getLanguage() {
        return language;
    }
    
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
