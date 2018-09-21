package boosterm.backend.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class SourceResponse {

    private String source;

    private int news;

    public SourceResponse(String source, int news) {
    	this.source = source;
    	this.news = news;
    }

    public String getSource() {
        return source;
    }

    public int getNews() {
        return news;
    }
}