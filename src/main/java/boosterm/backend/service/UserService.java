package boosterm.backend.service;

import boosterm.backend.domain.Term;
import boosterm.backend.domain.User;
import boosterm.backend.repo.GraphConfigRepo;
import boosterm.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    public UserRepo repo;

    @Autowired
    public GraphConfigRepo graphConfigRepo;

    private static Map<String, Boolean> DEFAULT_GRAPHS_CONFIG = new HashMap<String, Boolean>() {{
        put("search-region", true);
        put("search-time", true);
        put("news", true);
        put("twitter", true);
    }};

    public void createUser(String email) {
        User user = new User(email);
        repo.save(user);
    }

    public User getUser(String email) {
        return repo.get(email);
    }

    public Map<String, Boolean> getGraphsConfig(User user, Term term) {
        Map<String, Boolean> config =  graphConfigRepo.get(user, term);
        if (config == null) {
            return DEFAULT_GRAPHS_CONFIG;
        }
        return config;
    }

    public void saveGraphsConfig(User user, Term term, Map<String, Boolean> config) {
        graphConfigRepo.save(user, term, config);
    }

}
