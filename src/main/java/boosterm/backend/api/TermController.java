package boosterm.backend.api;

import boosterm.backend.api.request.TermRequest;
import boosterm.backend.api.request.TermUpdateRequest;
import boosterm.backend.api.response.TermResponse;
import boosterm.backend.domain.Term;
import boosterm.backend.domain.User;
import boosterm.backend.service.TermService;
import boosterm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/users/{userEmail}/terms")
public class TermController {

    @Autowired
    public TermService termService;

    @Autowired
    public UserService userService;

    @PostMapping
    public TermResponse saveTerm(@PathVariable String userEmail,
                                 @RequestBody TermRequest req) {
        return new TermResponse(termService.saveTerm(getUserByEmail(userEmail), req));
    }

    @PutMapping("/{term}")
    public TermResponse updateTerm(@PathVariable String userEmail,
                                   @PathVariable String term,
                                   @RequestBody TermUpdateRequest req) {
        User user = getUserByEmail(userEmail);
        Term savedTerm = getTermByName(user, term);
        return new TermResponse(termService.updateTerm(user, savedTerm, req));
    }

    @DeleteMapping("/{term}")
    public TermResponse updateTerm(@PathVariable String userEmail,
                                   @PathVariable String term) {
        User user = getUserByEmail(userEmail);
        Term savedTerm = getTermByName(user, term);
        return new TermResponse(termService.deleteTerm(user, savedTerm));
    }

    @GetMapping
    public List<TermResponse> getAllTerms(@PathVariable String userEmail) {

        return termService.getAllTerms(getUserByEmail(userEmail)).stream()
                .map(TermResponse::new).collect(Collectors.toList());
    }

    // Auxiliary

    private Term getTermByName(User user, String name) {
        Term term = termService.getTerm(user, name);
        if (term == null) {
            throw new RuntimeException("Term not found");
        }
        return term;
    }

    private User getUserByEmail(String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

}
