package boosterm.backend.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class SystemConfig {

    public static boolean prodEnv() {
        return Objects.equals(System.getenv("ENV"), "PROD");
    }

    public static String DEFAULT_TIMEZONE = "UTC";

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of(DEFAULT_TIMEZONE));
    }

}
