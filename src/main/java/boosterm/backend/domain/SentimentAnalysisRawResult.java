package boosterm.backend.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SentimentAnalysisRawResult {

    @SerializedName("sentence_list")
    private List<Sentence> sentences;

    public List<Sentence> getSentences() {
        return sentences;
    }

    public class Sentence {

        @SerializedName("score_tag")
        private String score;

        @SerializedName("confidence")
        private Integer confidence;

        public String getScore() {
            return score;
        }

        public Integer getConfidence() {
            return confidence;
        }
    }

}
