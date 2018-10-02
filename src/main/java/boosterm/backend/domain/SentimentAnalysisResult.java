package boosterm.backend.domain;

import java.math.BigDecimal;
import java.util.Map;

public class SentimentAnalysisResult {

    private int analysedElements;

    private Map<Sentiment, BigDecimal> sentimentPercentages;

    public SentimentAnalysisResult(int analysedElements, Map<Sentiment, BigDecimal> sentimentPercentages) {
        this.analysedElements = analysedElements;
        this.sentimentPercentages = sentimentPercentages;
    }

    public int getAnalysedElements() {
        return analysedElements;
    }

    public Map<Sentiment, BigDecimal> getSentimentPercentages() {
        return sentimentPercentages;
    }

}
