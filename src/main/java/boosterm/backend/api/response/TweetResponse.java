package boosterm.backend.api.response;

import boosterm.backend.domain.Tweet;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TweetResponse {

    private String user;

    private String text;

    private LocalDateTime date;

    private int retweets;

    private int favs;

    public TweetResponse(Tweet tweet) {
        this.user = tweet.getUser();
        this.text = tweet.getText();
        this.date = tweet.getDate();
        this.retweets = tweet.getRetweets();
        this.favs = tweet.getFavs();
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getRetweets() {
        return retweets;
    }

    public int getFavs() {
        return favs;
    }

}
