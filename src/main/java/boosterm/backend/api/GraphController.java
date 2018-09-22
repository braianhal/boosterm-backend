package boosterm.backend.api;

import boosterm.backend.api.response.ArticleResponse;
import boosterm.backend.api.response.SourceResponse;
import boosterm.backend.api.response.TweetResponse;
import boosterm.backend.domain.CustomDuration;
import boosterm.backend.domain.NewsSearch;
import boosterm.backend.domain.Sentiment;
import boosterm.backend.domain.TwitterSearch;
import boosterm.backend.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.valueOf;
import static java.util.stream.Collectors.toList;

@CrossOrigin
@RestController
@RequestMapping("/graphs")
public class GraphController {

    @Autowired
    public GraphService service;

    @GetMapping("/feeds/tweets")
    public List<TweetResponse> getTweetsFeed(@RequestParam String term,
                                             @RequestParam String lang,
                                             @RequestParam(name = "limit_amount") int limitAmount,
                                             @RequestParam(name = "limit_type") String limitType) {
        TwitterSearch search = new TwitterSearch(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));

        try {
            return service.getTweetFeed(search).stream().map(TweetResponse::new).collect(toList());
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve data");
        }
    }

    @GetMapping("/popularity/tweets")
    public Map<LocalDate, Integer> getPopularityInTimeForTweets(@RequestParam String term,
                                                                @RequestParam String lang,
                                                                @RequestParam(name = "limit_amount") int limitAmount,
                                                                @RequestParam(name = "limit_type") String limitType) {

    	TwitterSearch search = new TwitterSearch(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));
        try {
            return service.getPopularityValueInTimeForTweets(search);
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve data");
        }
    }

    @GetMapping("/sentiment/tweets")
    public Map<String, BigDecimal> getSentimentAnalysisForTweets(@RequestParam String term,
                                                                 @RequestParam String lang,
                                                                 @RequestParam(name = "limit_amount") int limitAmount,
                                                                 @RequestParam(name = "limit_type") String limitType) {
        TwitterSearch search = new TwitterSearch(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));
        try {
            return translatedSentimentMap(service.getSentimentAnalysisForTweets(search));
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve data");
        }
    }
    
	@GetMapping("/feeds/news")
    public List<ArticleResponse> getNewsFeed(@RequestParam String term,
    										 @RequestParam String lang,
    										 @RequestParam String from,
    										 @RequestParam String to) {	
		try {
			term = term.replace(" ", "%20");
			
			NewsSearch search = new NewsSearch(term, lang, from, to);
			
			List<ArticleResponse> articles = service.getNewsFeed(search);
			
			if (articles.size() < 10)
				articles = articles.subList(0, articles.size());
			else
				articles = articles.subList(0, 10);
			
			articles.forEach((article) -> {
				article.cleanStuffUp();
			});
			
			return articles;
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
    }

	@GetMapping("/sources/news")
    public List<SourceResponse> getSourcesForTerm(@RequestParam String term,
    									   		  @RequestParam String lang,
    									   		  @RequestParam String from,
    									   		  @RequestParam String to) {	
		try {
			term = term.replace(" ", "%20");
			
			NewsSearch search = new NewsSearch(term, lang, from, to);
			
			List<SourceResponse> sources = service.getSourcesForTerm(search);
			
			if (sources.size() < 5)
				sources = sources.subList(0, sources.size());
			else
				sources = sources.subList(0, 5);
			
			return sources;
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
    }

    // Auxiliary

    private Map<String, BigDecimal> translatedSentimentMap(Map<Sentiment, BigDecimal> sentimentMap) {
        Map<String, BigDecimal> translated = new HashMap<>();
        for (Map.Entry<Sentiment, BigDecimal> entry : sentimentMap.entrySet())
        {
            translated.put(entry.getKey().getTranslation(), entry.getValue());
        }
        return translated;
    }

}
