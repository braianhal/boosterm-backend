package boosterm.backend.api;

import boosterm.backend.api.exception.CantRetrieveDataExceptionResponse;
import boosterm.backend.api.exception.EmptySentimentListExceptionResponse;
import boosterm.backend.api.response.ArticleResponse;
import boosterm.backend.api.response.SentimentAnalysisResponse;
import boosterm.backend.api.response.SourceResponse;
import boosterm.backend.api.response.SourcesResponse;
import boosterm.backend.api.response.TweetResponse;
import boosterm.backend.domain.CustomDuration;
import boosterm.backend.domain.NewsSearch;
import boosterm.backend.domain.TwitterSearch;
import boosterm.backend.domain.exception.EmptySentimentListException;
import boosterm.backend.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
            throw new CantRetrieveDataExceptionResponse(e);
        }
    }

    @GetMapping("/popularity/tweets")
    public Map<String, Integer> getPopularityInTimeForTweets(@RequestParam String term,
                                                                @RequestParam String lang,
                                                                @RequestParam(name = "limit_amount") int limitAmount,
                                                                @RequestParam(name = "limit_type") String limitType) {

    	TwitterSearch search = new TwitterSearch(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));
        try {
            return service.getPopularityValueInTimeForTweets(search);
        } catch (Exception e) {
            throw new CantRetrieveDataExceptionResponse(e);
        }
    }

    @GetMapping("/sentiment/tweets")
    public SentimentAnalysisResponse getSentimentAnalysisForTweets(@RequestParam String term,
                                                                 @RequestParam String lang,
                                                                 @RequestParam(name = "limit_amount") int limitAmount,
                                                                 @RequestParam(name = "limit_type") String limitType) {
        TwitterSearch search = new TwitterSearch(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));
        try {
            return new SentimentAnalysisResponse(service.getSentimentAnalysisForTweets(search));
        } catch(EmptySentimentListException e) {
            throw new EmptySentimentListExceptionResponse(e);
        } catch (Exception e) {
            throw new CantRetrieveDataExceptionResponse(e);
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
            throw new CantRetrieveDataExceptionResponse(e);
		}
    }

	@GetMapping("/sources/news")
    public SourcesResponse getSourcesForTerm(@RequestParam String term,
    									   		  @RequestParam String lang,
    									   		  @RequestParam String from,
    									   		  @RequestParam String to) {	
		term = term.replace(" ", "%20");
		
		NewsSearch search = new NewsSearch(term, lang, from, to);
		
		try {
			List<SourceResponse> sources = service.getSourcesForTerm(search);
			
			if (sources.size() < 5)
				sources = sources.subList(0, sources.size());
			else
				sources = sources.subList(0, 5);
			
			int elements = sources.stream().mapToInt(element -> element.getNews()).sum();
			
			return new SourcesResponse(elements, sources);
			
		} catch (IOException e) {
            throw new CantRetrieveDataExceptionResponse(e);
		}
    }

    @GetMapping("/sentiment/news")
    public SentimentAnalysisResponse getSentimentAnalysisForNews(@RequestParam String term,
                                                               @RequestParam String lang,
                                                               @RequestParam String from,
                                                               @RequestParam String to) {
    	term = term.replace(" ", "%20");
    	
    	NewsSearch search = new NewsSearch(term, lang, from, to);
    	
        try {
            return new SentimentAnalysisResponse(service.getSentimentAnalysisForNews(search));
        } catch(EmptySentimentListException e) {
            throw new EmptySentimentListExceptionResponse(e);
        } catch (Exception e) {
            throw new CantRetrieveDataExceptionResponse(e);
        }
    }
    
}
