package boosterm.backend.domain;

import java.time.LocalDateTime;

public class Tweet {

    private String user;

    private String text;

    private LocalDateTime date;

    private int retweets;

    private int favs;

    public Tweet(String user, String text, LocalDateTime date, int retweets, int favs) {
        this.user = user;
        this.text = text;
        this.date = date;
        this.retweets = retweets;
        this.favs = favs;
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
