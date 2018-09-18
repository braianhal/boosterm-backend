package boosterm.backend.api;

import boosterm.backend.api.response.TweetResponse;
import boosterm.backend.domain.CustomDuration;
import boosterm.backend.domain.Search;
import boosterm.backend.domain.Sentiment;
import boosterm.backend.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        Search search = new Search(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));
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
        Search search = new Search(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));
        try {
            return service.getPopularityValueInTimeForTweets(search);
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve data");
        }
    }

    @GetMapping("/sentiment/tweets")
    public Map<Sentiment, BigDecimal> getSentimentAnalysisForTweets(@RequestParam String term,
                                                                    @RequestParam String lang,
                                                                    @RequestParam(name = "limit_amount") int limitAmount,
                                                                    @RequestParam(name = "limit_type") String limitType) {
        Search search = new Search(term, lang, new CustomDuration(limitAmount, valueOf(limitType)));
        try {
            return service.getSentimentAnalysisForTweets(search);
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve data");
        }
    }

}
