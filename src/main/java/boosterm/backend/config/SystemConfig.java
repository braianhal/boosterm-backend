package boosterm.backend.config;

import java.util.Objects;

public class SystemConfig {

    public static boolean prodEnv() {
        return Objects.equals(System.getenv("ENV"), "PROD");
    }

}
