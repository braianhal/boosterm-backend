package boosterm.backend.client;

import boosterm.backend.domain.Sentiment;
import boosterm.backend.domain.SentimentAnalysisRawResult;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static boosterm.backend.domain.Sentiment.fromScoreTag;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;
import static org.apache.tomcat.util.buf.StringUtils.join;

@Service
public class MeaningCloudClient {

    @Value("${api.meaning_cloud.key}")
    private String apiKey;

    @Value("${api.meaning_cloud.url.sentiment}")
    private String apiSentimentUrl;

    @Autowired
    private Gson gson;

    private String getSentimentData(String text, String language) throws UnirestException {
        return Unirest.post(apiSentimentUrl)
                .header("content-type", "application/x-www-form-urlencoded")
                .body(
                        "key="+ apiKey +
                        "&lang=" + language +
                        "&txt=" + text +
                        "&txtf=plain").asString().getBody();
    }

    public Map<Sentiment, BigDecimal> getSentimentsValues(List<String> texts, String language) throws UnirestException {
        HashMap<Sentiment, BigDecimal> valuesMap = initializeValuesMap();
        List<SentimentAnalysisRawResult.Sentence> resultSentencesList = gson.fromJson(getSentimentData(join(texts, '.'), language), SentimentAnalysisRawResult.class).getSentences();
        resultSentencesList.forEach(sentence -> {
            Sentiment sentiment = fromScoreTag(sentence.getScore());
            valuesMap.put(sentiment,
                    valuesMap.get(sentiment).add(confidenceToCountValue(sentence.getConfidence())));
        });
        return valuesMap;
    }

    private HashMap<Sentiment, BigDecimal> initializeValuesMap() {
        HashMap<Sentiment, BigDecimal> map = new HashMap<>();
        Arrays.asList(Sentiment.values()).forEach(sentiment ->
            map.put(sentiment, ZERO)
        );
        return map;
    }

    private static BigDecimal confidenceToCountValue(Integer confidence) {
        return new BigDecimal(confidence).divide(new BigDecimal(100), 2, ROUND_HALF_UP);
    }

}
