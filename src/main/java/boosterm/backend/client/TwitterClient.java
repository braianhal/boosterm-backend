package boosterm.backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> test(String text) throws TwitterException {
        Query query = new Query(text);
        QueryResult result = twitter.search(query);
        return result.getTweets().stream()
                .map(Status::getText)
                .collect(Collectors.toList());
    }

}
