package boosterm.backend.service;

import boosterm.backend.client.TwitterClient;
import boosterm.backend.domain.Search;
import boosterm.backend.domain.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static boosterm.backend.config.SystemConfig.now;

@Service
public class GraphService {

    @Autowired
    public TwitterClient twitter;

    private static int TWEET_FEED_COUNT = 10;

    private static int TWEET_POPULARIRY_COUNT = 100;

    public List<Tweet> getTweetFeed(Search search) throws TwitterException {
        LocalDateTime now = now();
        LocalDateTime since = search.sinceDate(now);
        return twitter.searchRelevantTweets(search.getTerm(), search.getLanguage(), since, now, TWEET_FEED_COUNT);
    }

    public Map<LocalDate, Integer> getPopularityValueInTimeForTweets(Search search) throws TwitterException {
        LocalDate now = now().toLocalDate();
        LocalDate since = search.sinceDate(now);
        HashMap<LocalDate, Integer> popularityMap = new HashMap<>();
        while (!since.isAfter(now)) {
            int dayPopularity = twitter.searchRelevantTweets(search.getTerm(), search.getLanguage(),
                    since.atStartOfDay(), since.plusDays(1).atStartOfDay(), TWEET_POPULARIRY_COUNT)
                    .stream().mapToInt(Tweet::getRetweets).sum();
            popularityMap.put(since, dayPopularity);
            since = since.plusDays(1);
        }
        return popularityMap;
    }

}
