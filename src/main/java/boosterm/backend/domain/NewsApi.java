package boosterm.backend.domain;

import java.util.List;

public class NewsApi {

	private String status;
	
	private int totalResults;
	
	private List<Article> articles;
	
    public NewsApi() {
        super();
    }
	
    public NewsApi(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
    
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public void setTotalResults(int results) {
        totalResults = results;
    }

    public void setArticles(List<Article> myArticles) {
        articles = myArticles;
    }
}