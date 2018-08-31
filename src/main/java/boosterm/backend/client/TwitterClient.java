package boosterm.backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwitterClient {

    @Value("${api.twitter.key}")
    private String apiKey;

    @Value("${api.twitter.key_secret}")
    private String apiKeySecret;

    @Value("${api.twitter.access_token}")
    private String accessToken;

    @Value("${api.twitter.access_token_secret}")
    private String accessTokenSecret;

}
