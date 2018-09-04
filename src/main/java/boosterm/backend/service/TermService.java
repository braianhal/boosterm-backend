package boosterm.backend.service;

import boosterm.backend.api.request.TermRequest;
import boosterm.backend.api.request.TermUpdateRequest;
import boosterm.backend.domain.Term;
import boosterm.backend.domain.User;
import boosterm.backend.repo.TermRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {

    @Autowired
    public TermRepo repo;

    public Term saveTerm(User user, TermRequest termData) {
        Term term = new Term(termData.getTerm(), termData.getDescription());
        repo.save(user, term);
        return term;
    }

    public Term updateTerm(User user, Term term, TermUpdateRequest updatedTermData) {
        Term updatedTerm = new Term(term.getTerm(), updatedTermData.getDescription());
        repo.save(user, updatedTerm);
        return term;
    }

    public Term deleteTerm(User user, Term term) {
        repo.delete(user, term);
        return term;
    }

    public List<Term> getAllTerms(User user) {
        return repo.getAll(user);
    }

    public Term getTerm(User user, String name) {
        return repo.get(user, name);
    }

}
