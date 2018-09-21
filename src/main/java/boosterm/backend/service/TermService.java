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

    public void saveTerm(User user, TermRequest termData) {
        Term term = new Term(termData.getCode(), termData.getName(), termData.getType(), termData.getDescription());
        repo.save(user, term);
    }

    public void updateTerm(User user, Term term, TermUpdateRequest updatedTermData) {
        Term updatedTerm = new Term(term.getCode(), term.getName(), term.getType(), updatedTermData.getDescription());
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
