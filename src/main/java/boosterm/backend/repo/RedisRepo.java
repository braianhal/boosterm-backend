package boosterm.backend.repo;

public interface RedisRepo<T> {

    T get(String id);

    void save(T entity);

    default String getKey(String format, String id) {
        return String.format(format, id);
    }

}
