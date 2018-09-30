package boosterm.backend.api;

import boosterm.backend.api.exception.CantRetrieveDataExceptionResponse;
import boosterm.backend.api.exception.EmptySentimentListExceptionResponse;
import boosterm.backend.api.exception.NotFoundExceptionResponse;
import boosterm.backend.api.response.ArticleResponse;
import boosterm.backend.api.response.SourceResponse;
import boosterm.backend.api.response.TweetResponse;
import boosterm.backend.domain.*;
import boosterm.backend.domain.exception.EmptySentimentListException;
import boosterm.backend.service.GraphService;
import boosterm.backend.service.TermService;
import boosterm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.valueOf;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@CrossOrigin
@RestController
@RequestMapping("/graphs")
public class GraphController {

    @Autowired
    public GraphService service;

    @Autowired
    public TermService termService;

    @Autowired
    public UserService userService;

    @GetMapping("/config")
    public Map<String, Boolean> getGraphConfigResponse(@RequestHeader("X-Auth-Mail") String userEmail,
                                                       @RequestParam("code") String termCode) {
        User user = getUserByEmail(userEmail);
        Term term = getTermByCode(user, termCode);
        return service.getGraphsConfig(user, term);
    }

    @PutMapping("/config")
    @ResponseStatus(NO_CONTENT)
    public void saveGraphConfig(@RequestHeader("X-Auth-Mail") String userEmail,
                                @RequestParam("code") String termCode,
                                @RequestBody Map<String, Boolean> config) {
        User user = getUserByEmail(userEmail);
        Term term = getTermByCode(user, termCode);
        service.saveGraphsConfig(user, term, config);
    }


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
    public Map<LocalDate, Integer> getPopularityInTimeForTweets(@RequestParam String term,
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
    public Map<String, BigDecimal> getSentimentAnalysisForTweets(@RequestParam String term,
                                                                 @RequestParam String lang,
                                                                 @RequestParam(name = "limit_amount") int limitAmount,
                                                                 @RequestParam(name = "limit_type") String limitType) {
        TwitterSearch search = new TwitterSearch(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));
        try {
            return translatedAndSortedSentimentMap(service.getSentimentAnalysisForTweets(search));
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
    public List<SourceResponse> getSourcesForTerm(@RequestParam String term,
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
			
			return sources;
			
		} catch (IOException e) {
            throw new CantRetrieveDataExceptionResponse(e);
		}
    }

    @GetMapping("/sentiment/news")
    public Map<String, BigDecimal> getSentimentAnalysisForNews(@RequestParam String term,
                                                               @RequestParam String lang,
                                                               @RequestParam String from,
                                                               @RequestParam String to) {
    	term = term.replace(" ", "%20");
    	
    	NewsSearch search = new NewsSearch(term, lang, from, to);
    	
        try {
            return translatedAndSortedSentimentMap(service.getSentimentAnalysisForNews(search));
        } catch(EmptySentimentListException e) {
            throw new EmptySentimentListExceptionResponse(e);
        } catch (Exception e) {
            throw new CantRetrieveDataExceptionResponse(e);
        }
    }
    
    // Auxiliary

    private Map<String, BigDecimal> translatedAndSortedSentimentMap(Map<Sentiment, BigDecimal> sentimentMap) {
        Map<String, BigDecimal> newMap = new LinkedHashMap<>();
        for (Sentiment sentiment : Sentiment.values()) {
            newMap.put(sentiment.getTranslation(), sentimentMap.get(sentiment));
        }
        return newMap;
    }

    private Term getTermByCode(User user, String code) {
        Term term = termService.getTerm(user, code);
        if (term == null) {
            throw new NotFoundExceptionResponse("Term not found");
        }
        return term;
    }

    private User getUserByEmail(String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new NotFoundExceptionResponse("User not found");
        }
        return user;
    }

}
