package boosterm.backend.api;

import boosterm.backend.client.GoogleNewsClient;
import boosterm.backend.client.MeaningCloudClient;
import boosterm.backend.client.TwitterClient;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    public GoogleNewsClient googleNewsClient;

    @Autowired
    public MeaningCloudClient meaningCloudClient;

    @Autowired
    public TwitterClient twitterClient;

    @GetMapping("/news")
    public String newsTest(@RequestParam String text) {
        googleNewsClient.testMethod(text);
        return "Ok!";
    }

    @GetMapping("/sentiment")
    public String sentimentTest(@RequestParam String text) {
        try {
            return meaningCloudClient.testMethod(text);
        } catch (UnirestException e) {
            return "ERROR" + e.getMessage();
        }
    }

    @GetMapping("/twitter")
    public List<String> twittertest(@RequestParam String text) {
        try {
            return twitterClient.test(text);
        } catch (TwitterException e) {
            return new ArrayList<>();
        }
    }

}
