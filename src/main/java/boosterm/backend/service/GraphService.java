package boosterm.backend.service;

import boosterm.backend.api.response.ArticleResponse;
import boosterm.backend.client.TwitterClient;
import boosterm.backend.domain.TwitterSearch;
import boosterm.backend.domain.NewsApi;
import boosterm.backend.domain.NewsSearch;
import boosterm.backend.domain.Tweet;

import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import twitter4j.TwitterException;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.InputStream;
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

    @Value("${api.news.key}")
    private String newsApiKey;
    
    private static int TWEET_FEED_COUNT = 10;

    private static int TWEET_POPULARIRY_COUNT = 100;

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

}
