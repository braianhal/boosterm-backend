package boosterm.backend.client;

import boosterm.backend.utils.SystemConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisClient {

    @Value("${redis.url.dev}")
    private String urlDev;

    @Value("${redis.url.prod}")
    private String urlProd;

    @Value("${redis.port}")
    private int port;

    private Jedis getRedis() {
        String url = SystemConfig.prodEnv() ? urlProd : urlDev;
        return new JedisPool(url, port).getResource();
    }

    public String set(String key, String value) {
        return getRedis().set(key, value);
    }

    public String get(String key) {
        return getRedis().get(key);
    }

}
