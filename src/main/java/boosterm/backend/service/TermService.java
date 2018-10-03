package boosterm.backend.service;

import boosterm.backend.api.request.TermRequest;
import boosterm.backend.api.request.TermUpdateRequest;
import boosterm.backend.domain.Term;
import boosterm.backend.domain.User;
import boosterm.backend.repo.GraphConfigRepo;
import boosterm.backend.repo.TermRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static boosterm.backend.config.SystemConfig.now;

@Service
public class TermService {

    @Autowired
    public TermRepo repo;

    @Autowired
    public GraphConfigRepo graphConfigRepo;

    private static Map<String, Boolean> DEFAULT_GRAPHS_CONFIG = new HashMap<String, Boolean>() {{
        put("search-region", true);
        put("search-time", true);
        put("news", true);
        put("twitter", true);
    }};

    public void saveTerm(User user, TermRequest termData) {
        Term term = new Term(termData.getCode(), termData.getName(), termData.getType(), termData.getDescription(), now(), DEFAULT_GRAPHS_CONFIG);
        repo.save(user, term);
    }

    public void updateTerm(User user, Term term, TermUpdateRequest updatedTermData) {
        String description = updatedTermData.getDescription() == null ? term.getDescription() : updatedTermData.getDescription();
        Map<String, Boolean> graphsConfig = updatedTermData.getGraphsConfig() == null ? term.getGraphsConfig() : updatedTermData.getGraphsConfig();
        Term updatedTerm = new Term(term.getCode(), term.getName(), term.getType(), description, now(), graphsConfig);
        repo.save(user, updatedTerm);
    }

    public void deleteTerm(User user, Term term) {
        repo.delete(user, term);
    }

    public List<Term> getAllTerms(User user) {
        return repo.getAll(user);
    }

    public Term getTerm(User user, String code) {
        return repo.get(user, code);
    }

}
