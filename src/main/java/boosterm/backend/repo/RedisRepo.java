package boosterm.backend.repo;

public interface RedisRepo<T> {

    default String getKey(String format, String... params) {
        return String.format(format, params);
    }

}
