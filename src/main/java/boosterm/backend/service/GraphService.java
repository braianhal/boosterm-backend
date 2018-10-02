package boosterm.backend.service;

import boosterm.backend.api.response.ArticleResponse;
import boosterm.backend.api.response.SourceResponse;
import boosterm.backend.client.MeaningCloudClient;
import boosterm.backend.client.TwitterClient;
import boosterm.backend.domain.*;
import boosterm.backend.domain.exception.EmptySentimentListException;
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
import java.util.*;
import java.util.function.Predicate;

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

    private static int TWEET_DAILY_SENTIMENT_COUNT = 30;

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
    	
    	String url = "https://newsapi.org/v2/everything?q=" + search.getTerm() +
    			"&language=" + search.getLanguage() +
    			"&from=" + search.getFrom() +
    			"&to=" + search.getTo() +
    			"&pageSize=100&sortBy=popularity&apiKey=" + newsApiKey;
		InputStream stream = Request.Get(url)
				.execute().returnContent().asStream();
	    	
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
				   .registerModule(new Jdk8Module())
				   .registerModule(new JavaTimeModule());
		
		NewsApi news = objectMapper.readValue(stream, NewsApi.class);
		
		Predicate<ArticleResponse> predicate1 = article->article.getContent() == null;
		Predicate<ArticleResponse> predicate = article->(/*!article.getTitle().replace(" ", "%20").toLowerCase().contains(search.getTerm().toLowerCase()) ||*/
				!article.getContent().replace(" ", "%20").toLowerCase().contains(search.getTerm().toLowerCase()));
		
		List<ArticleResponse> articles = news.getArticles().stream().map(ArticleResponse::new).collect(toList());
		
		articles.removeIf(predicate1);
		articles.removeIf(predicate);
		
		return articles;
    }
    
    public List<SourceResponse> getSourcesForTerm(NewsSearch search) throws IOException {
    	
    	List<ArticleResponse> articles = this.getNewsFeed(search);
    	
    	HashMap<String, Integer> map = new HashMap<>();
    	
    	articles.forEach((article)-> {
    		String source = article.getSource();
    	
    		if(map.containsKey(source)) {
    			int newValue = map.get(source) + 1;
    			map.replace(source, newValue);
    		}
    		else
    			map.put(source, 1);
    	});
    	
    	
    	return this.createSourceResponseFromMap(map);    	
    }

    public SentimentAnalysisResult getSentimentAnalysisForTweets(TwitterSearch search) throws TwitterException, UnirestException {
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
    
    public SentimentAnalysisResult getSentimentAnalysisForNews(NewsSearch search) throws IOException, UnirestException {
        List<ArticleResponse> articles = getNewsFeed(search);
        
        return calculateSentimentPercentages(articles.stream().map(ArticleResponse::getContent).collect(toList()), search);
    }

    private SentimentAnalysisResult calculateSentimentPercentages(List<String> texts, Search search) throws UnirestException {
        if (texts.isEmpty()) {
            throw new EmptySentimentListException();
        }
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
        return new SentimentAnalysisResult(texts.size(), sentimentValues);
    }
    
    private List<SourceResponse> createSourceResponseFromMap(HashMap<String, Integer> map) {
    	
    	List<SourceResponse> sources = new ArrayList<SourceResponse>();
    	
    	map.forEach((source, news)-> {
    		sources.add(new SourceResponse(source, news));
    	});
    	
    	Collections.sort(sources, new Comparator<SourceResponse>() {
    	    @Override
    	    public int compare(SourceResponse source1, SourceResponse source2) {
    	        return source1.getNews() > source2.getNews() ? -1 : (source1.getNews() < source2.getNews()) ? 1 : 0;
    	    };
    	});
    	
    	return sources;
    }

}
