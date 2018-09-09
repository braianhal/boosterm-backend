package boosterm.backend.api;

import boosterm.backend.api.response.TweetResponse;
import boosterm.backend.domain.CustomDuration;
import boosterm.backend.domain.Search;
import boosterm.backend.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
        Search search = new Search(term, lang, new CustomDuration(limitAmount, ChronoUnit.valueOf(limitType)));
        try {
            return service.getTweetFeed(search).stream().map(TweetResponse::new).collect(toList());
        } catch (TwitterException e) {
            throw new RuntimeException("Can't retrieve tweets");
        }
    }

}
