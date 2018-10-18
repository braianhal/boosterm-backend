package boosterm.backend.client;

import boosterm.backend.domain.Tweet;
import boosterm.backend.utils.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import static boosterm.backend.config.SystemConfig.DEFAULT_TIMEZONE;
import static boosterm.backend.utils.Converter.toLocalDateTime;
import static java.util.stream.Collectors.toList;
import static twitter4j.Query.ResultType.mixed;
import static twitter4j.Query.ResultType.popular;

@Service
public class TwitterClient {

    @Value("${api.twitter.key}")
    private String apiKey;

    @Value("${api.twitter.key_secret}")
    private String apiKeySecret;

    @Value("${api.twitter.access_token}")
    private String accessToken;

    @Value("${api.twitter.access_token_secret}")
    private String accessTokenSecret;

    private Twitter twitter;

    private static String DATE_LIMIT_FORMAT = "yyyy-MM-dd";

    @PostConstruct
    private void init() {
        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setOAuthConsumerKey(apiKey)
                .setOAuthConsumerSecret(apiKeySecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public List<Tweet> searchRelevantTweets(String text, String language, LocalDateTime limitSince, LocalDateTime limitTo, int quantity) throws TwitterException {
        Query query = getDefaultSearch(text, language, limitSince, limitTo, quantity);
        query.setResultType(popular);
        return twitter.search(query).getTweets().stream().map(TwitterClient::toTweet).collect(toList());
    }

    public List<Tweet> searchPopularAndRecentTweets(String text, String language, LocalDateTime limitSince, LocalDateTime limitTo, int quantity) throws TwitterException {
        Query query = getDefaultSearch(text, language, limitSince, limitTo, quantity);
        query.setResultType(mixed);
        return twitter.search(query).getTweets().stream().map(TwitterClient::toTweet).collect(toList());
    }

    private Query getDefaultSearch(String text, String language, LocalDateTime limitSince, LocalDateTime limitTo, int quantity) {
        Query query = new Query(text);
        query.setCount(quantity);
        query.setLang(language);
        query.setSince(Converter.toString(limitSince, DATE_LIMIT_FORMAT));
        query.setUntil(Converter.toString(limitTo, DATE_LIMIT_FORMAT));
        return query;
    }

    private static Tweet toTweet(Status tweetData) {
        return new Tweet(Long.toString(tweetData.getId()), tweetData.getUser().getScreenName(), tweetData.getText(),
                toLocalDateTime(tweetData.getCreatedAt(), DEFAULT_TIMEZONE),
                tweetData.getRetweetCount(), tweetData.getFavoriteCount());
    }

}
