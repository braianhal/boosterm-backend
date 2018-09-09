package boosterm.backend.domain;

import java.time.temporal.ChronoUnit;

public class CustomDuration {

    private int amount;

    private ChronoUnit unit;

    public CustomDuration(int amount, ChronoUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public ChronoUnit getUnit() {
        return unit;
    }

}
