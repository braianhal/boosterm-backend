package boosterm.backend.api.response;

import boosterm.backend.domain.Article;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(SnakeCaseStrategy.class)
public class ArticleResponse {

    private String source;

    private String title;

    private String content;
    
    private String url;
    
    private String urlToImage;

    private LocalDateTime publishedAt;

    public ArticleResponse(Article article) {
    	this.source = article.getSource().getName();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.url = article.getUrl();
        this.urlToImage = article.getUrlToImage();
        this.publishedAt = article.getPublishedAt();
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    
    public String getUrl() {
    	return url;
    }
    
    public String getUrlToImage() {
    	return urlToImage;
    }
    
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
    
    public void cleanStuffUp() {
	    content = content.split("(\\[)(\\+)")[0];
	    
	    if (source.equals("La Nacion"))
	    	urlToImage = "https:" + urlToImage;
    }
}