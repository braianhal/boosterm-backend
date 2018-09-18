package boosterm.backend.domain;

public enum Sentiment {

    VERY_POSITIVE, POSITIVE, NEUTRAL, NEGATIVE, VERY_NEGATIVE, NONE;

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

}
