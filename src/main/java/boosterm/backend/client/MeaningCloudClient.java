package boosterm.backend.client;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MeaningCloudClient {

    @Value("${api.meaning_cloud.key}")
    private String apiKey;

    @Value("${api.meaning_cloud.url.sentiment}")
    private String apiSentimentUrl;

    private static String DEFAULT_LANGUAGE = "es";

    public String testMethod(String text) throws UnirestException {
        return Unirest.post(apiSentimentUrl)
                .header("content-type", "application/x-www-form-urlencoded")
                .body(
                        "key="+ apiKey +
                        "&lang=" + DEFAULT_LANGUAGE +
                        "&txt=" + text +
                        "&txtf=plain")
                .asString().getBody();
    }

}
