package boosterm.backend.client;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GoogleNewsClient {

    @Value("${api.news.key}")
    private String apiKey;

    private NewsApiClient apiClient;

    private static String DEFAULT_LANGUAGE = "es";

    @PostConstruct
    private void init() {
        apiClient = new NewsApiClient(apiKey);
    }

    public void testMethod(String text) {
        apiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .q(text)
                        .language(DEFAULT_LANGUAGE)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        System.out.println(response.getArticles().get(0).getTitle());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
    }

}
