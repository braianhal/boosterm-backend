package boosterm.backend.domain;

public enum Sentiment {

    VERY_POSITIVE("Muy positivo"),
    POSITIVE("Positivo"),
    NEUTRAL("Neutral"),
    NEGATIVE("Negativo"),
    VERY_NEGATIVE("Muy negativo"),
    NONE("No detectado");

    private String translation;

    Sentiment(String translation) {
        this.translation = translation;
    }

    public static Sentiment fromScoreTag(String tag) {
        switch (tag) {
            case "P+":
                return VERY_POSITIVE;
            case "P":
                return POSITIVE;
            case "NEU":
                return NEUTRAL;
            case "N":
                return NEGATIVE;
            case "N+":
                return VERY_NEGATIVE;
        }
        return NONE;
    }

    public String getTranslation() {
        return this.translation;
    }

}
