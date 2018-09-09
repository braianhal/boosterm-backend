package boosterm.backend.service;

import boosterm.backend.client.TwitterClient;
import boosterm.backend.domain.Search;
import boosterm.backend.domain.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static boosterm.backend.config.SystemConfig.DEFAULT_TIMEZONE;
import static java.time.LocalDateTime.now;

@Service
public class GraphService {

    @Autowired
    public TwitterClient twitter;

    private static int TWEET_FEED_COUNT = 10;

    public List<Tweet> getTweetFeed(Search search) throws TwitterException {
        LocalDateTime now = now(ZoneId.of(DEFAULT_TIMEZONE))
                .minus(search.getTimeLimit().getAmount(), search.getTimeLimit().getUnit());
        return twitter.searchRelevantTweets(search.getTerm(), search.getLanguage(), now, TWEET_FEED_COUNT);
    }

}
