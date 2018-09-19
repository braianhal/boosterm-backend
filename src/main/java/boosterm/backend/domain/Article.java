package boosterm.backend.domain;

import java.time.LocalDateTime;

public class Article {
	
	private Source source;

	private String author;
	
    private String title;

    private String description;
    
    private String content;
    
    private String url;
    
    private String urlToImage;

    private LocalDateTime publishedAt;

    public Article() {
        super();
    }
    
    public Article(Source source, String author, String title, String description, String content, String url, String urlToImage, LocalDateTime publishedAt) {
        this.source = source;
    	this.author = author;
        this.title = title;
        this.description = description;
        this.content = content;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public Source getSource() {
    	return source;
    }
    
    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
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
}
