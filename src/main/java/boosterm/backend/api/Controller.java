package boosterm.backend.api;

import boosterm.backend.client.GoogleNewsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    public GoogleNewsClient googleNewsClient;

    @GetMapping("/news")
    public String newsTest(@RequestParam String text) {
        googleNewsClient.testMethod(text);
        return "Ok!";
    }

}
