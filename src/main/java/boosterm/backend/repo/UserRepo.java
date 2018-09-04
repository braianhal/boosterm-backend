package boosterm.backend.repo;

import boosterm.backend.domain.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class UserRepo implements RedisRepo<User> {

    @Autowired
    public Gson gson;

    @Autowired
    public Jedis redis;

    private static String USER_KEY_FORMAT = "user:%s";

    public User get(String email) {
        return gson.fromJson(redis.get(getKey(USER_KEY_FORMAT, email)), User.class);
    }

    public void save(User user) {
        redis.set(getKey(USER_KEY_FORMAT, user.getEmail()), gson.toJson(user));
    }

}
