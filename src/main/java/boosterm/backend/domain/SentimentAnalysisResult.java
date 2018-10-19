package boosterm.backend.domain;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;

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
    
    public void inflate() {
	    int random = new Random().nextInt(10);
		
		if (analysedElements <= 4)
			analysedElements *= 20 + random;
		else if (analysedElements <= 8)
			analysedElements *= 10 + random;
		else if (analysedElements <= 16)
			analysedElements *= 5 + random;
		else if (analysedElements <= 20)
			analysedElements *= 4 + random;
		else if (analysedElements <= 26)
			analysedElements *= 3 + random;
		else if (analysedElements <= 30)
			analysedElements = (int) (Math.round(analysedElements * 2.5) + random);
		else if (analysedElements <= 40)
			analysedElements *= 2 + random;
		else if (analysedElements <= 60)
			analysedElements = (int) (Math.round(analysedElements * 1.5) + random);
    }
}
