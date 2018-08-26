package boosterm.backend.utils;

import java.util.Objects;

public class SystemConfig {

    public static boolean prodEnv() {
        return Objects.equals(System.getenv("ENV"), "PROD");
    }

}
