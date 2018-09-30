package boosterm.backend.repo;

import boosterm.backend.domain.Term;
import boosterm.backend.domain.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Component
public class GraphConfigRepo implements RedisRepo<Term> {

    @Autowired
    public Gson gson;

    @Autowired
    public Jedis redis;

    private static String GRAPH_CONFIG_KEY_FORMAT = "user:%s:terms:%s::graphs:config";

    public void save(User user, Term term, Map<String, Boolean> config) {
        redis.set(getKey(GRAPH_CONFIG_KEY_FORMAT, user.getEmail(), term.getCode()),  gson.toJson(config));
    }

    public Map<String, Boolean> get(User user, Term term) {
        return gson.fromJson(redis.get(getKey(GRAPH_CONFIG_KEY_FORMAT, user.getEmail(), term.getCode())), Map.class);
    }

}
