package boosterm.backend.repo;

import boosterm.backend.domain.Term;
import boosterm.backend.domain.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TermRepo implements RedisRepo<Term> {

    @Autowired
    public Gson gson;

    @Autowired
    public Jedis redis;

    private static String TERMS_LIST_KEY_FORMAT = "user:%s:terms";

    private static String TERM_KEY_FORMAT = "user:%s:term:%s";

    public Term get(User user, String name) {
        return gson.fromJson(redis.get(getKey(TERM_KEY_FORMAT, user.getEmail(), name)), Term.class);
    }

    public List<Term> getAll(User user) {
        Set<String> terms = redis.smembers(getKey(TERMS_LIST_KEY_FORMAT, user.getEmail()));
        return terms.stream().map(term -> get(user, term)
        ).collect(Collectors.toList());
    }

    public void save(User user, Term term) {
        redis.sadd(getKey(TERMS_LIST_KEY_FORMAT, user.getEmail()), term.getTerm());
        redis.set(getKey(TERM_KEY_FORMAT, user.getEmail(), term.getTerm()),  gson.toJson(term));
    }

    public void delete(User user, Term term) {
        redis.srem(getKey(TERMS_LIST_KEY_FORMAT, user.getEmail()), term.getTerm());
        redis.del(getKey(TERM_KEY_FORMAT, user.getEmail(), term.getTerm()));
    }

}
