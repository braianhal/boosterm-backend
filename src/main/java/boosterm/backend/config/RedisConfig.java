package boosterm.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class RedisConfig {

    @Value("${redis.url.dev}")
    private String urlDev;

    @Value("${redis.url.prod}")
    private String urlProd;

    @Value("${redis.port}")
    private int port;

    @Bean
    public Jedis getRedis() {
        String url = SystemConfig.prodEnv() ? urlProd : urlDev;
        return new JedisPool(url, port).getResource();
    }

}
