package boosterm.backend.api.response;

import boosterm.backend.domain.Sentiment;
import boosterm.backend.domain.SentimentAnalysisResult;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SentimentAnalysisResponse {

    private int analysedElements;

    private Map<String, BigDecimal> sentimentPercentages;

    public SentimentAnalysisResponse(SentimentAnalysisResult result) {
        this.analysedElements = result.getAnalysedElements();
        this.sentimentPercentages = translatedAndSortedSentimentMap(result.getSentimentPercentages());
    }

    public int getAnalysedElements() {
        return analysedElements;
    }

    public Map<String, BigDecimal> getSentimentPercentages() {
        return sentimentPercentages;
    }

    private Map<String, BigDecimal> translatedAndSortedSentimentMap(Map<Sentiment, BigDecimal> sentimentMap) {
        Map<String, BigDecimal> newMap = new LinkedHashMap<>();
        for (Sentiment sentiment : Sentiment.values()) {
            newMap.put(sentiment.getTranslation(), sentimentMap.get(sentiment));
        }
        return newMap;
    }

}
