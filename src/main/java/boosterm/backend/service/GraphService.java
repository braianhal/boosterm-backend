package boosterm.backend.service;

import boosterm.backend.api.response.ArticleResponse;
import boosterm.backend.client.MeaningCloudClient;
import boosterm.backend.client.TwitterClient;
import boosterm.backend.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static boosterm.backend.config.SystemConfig.now;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;

@Service
public class GraphService {

    @Autowired
    public TwitterClient twitter;

    @Value("${api.news.key}")
    private String newsApiKey;
    
    @Autowired
    public MeaningCloudClient meaningCloud;

    private static int TWEET_FEED_COUNT = 10;

    private static int TWEET_POPULARIRY_COUNT = 100;

    private static int TWEET_DAILY_SENTIMENT_COUNT = 25;

    public List<Tweet> getTweetFeed(TwitterSearch search) throws TwitterException {
        LocalDateTime now = now();
        LocalDateTime since = search.sinceDate(now);
        return twitter.searchRelevantTweets(search.getTerm(), search.getLanguage(), since, now, TWEET_FEED_COUNT);
    }

    public Map<LocalDate, Integer> getPopularityValueInTimeForTweets(TwitterSearch search) throws TwitterException {
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
    
    public List<ArticleResponse> getNewsFeed(NewsSearch search) throws IOException {
    	
    	String url = "https://newsapi.org/v2/everything?q=" + search.getTerm() + "&language=" + search.getLanguage() + "&sortBy=publishedAt&apiKey=" + newsApiKey;
		InputStream stream = Request.Get(url)
				.execute().returnContent().asStream();
	    	
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
				   .registerModule(new Jdk8Module())
				   .registerModule(new JavaTimeModule());
		
		NewsApi news = objectMapper.readValue(stream, NewsApi.class);
		
		return news.getArticles().stream().map(ArticleResponse::new).collect(toList());
    }

    public Map<Sentiment, BigDecimal> getSentimentAnalysisForTweets(TwitterSearch search) throws TwitterException, UnirestException {
        LocalDate now = now().toLocalDate();
        LocalDate since = search.sinceDate(now);
        List<String> tweets = new ArrayList<>();
        while (!since.isAfter(now)) {
            tweets.addAll(twitter.searchRelevantTweets(search.getTerm(), search.getLanguage(),
                    since.atStartOfDay(), since.plusDays(1).atStartOfDay(), TWEET_DAILY_SENTIMENT_COUNT)
                    .stream().map(Tweet::getText).collect(toList()));
            since = since.plusDays(1);
        }
        return calculateSentimentPercentages(tweets, search);
    }

    private Map<Sentiment, BigDecimal> calculateSentimentPercentages(List<String> texts, TwitterSearch search) throws UnirestException {
        Map<Sentiment, BigDecimal> sentimentValues = meaningCloud.getSentimentsValues(texts, search.getLanguage());
        BigDecimal total = ZERO;
        for (Map.Entry<Sentiment, BigDecimal> entry : sentimentValues.entrySet())
        {
            total = total.add(entry.getValue());
        }
        for (Map.Entry<Sentiment, BigDecimal> entry : sentimentValues.entrySet())
        {
            sentimentValues.put(entry.getKey(), entry.getValue().divide(total, 2, HALF_UP));
        }
        return sentimentValues;
    }

}
