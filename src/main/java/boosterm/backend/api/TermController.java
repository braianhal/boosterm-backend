package boosterm.backend.api;

import boosterm.backend.api.exception.NotFoundException;
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

import static org.springframework.http.HttpStatus.NO_CONTENT;

@CrossOrigin
@RestController
@RequestMapping("/users/terms")
public class TermController {

    @Autowired
    public TermService termService;

    @Autowired
    public UserService userService;

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    public void saveTerm(@RequestHeader("X-Auth-Mail") String userEmail,
                         @RequestBody TermRequest req) {
        termService.saveTerm(getUserByEmail(userEmail), req);
    }

    @PutMapping("/{termName}")
    @ResponseStatus(NO_CONTENT)
    public void updateTerm(@RequestHeader("X-Auth-Mail") String userEmail,
                           @PathVariable String termName,
                           @RequestBody TermUpdateRequest req) {
        User user = getUserByEmail(userEmail);
        Term savedTerm = getTermByName(user, termName);
        termService.updateTerm(user, savedTerm, req);
    }

    @DeleteMapping("/{termName}")
    @ResponseStatus(NO_CONTENT)
    public void updateTerm(@RequestHeader("X-Auth-Mail") String userEmail,
                           @PathVariable String termName) {
        User user = getUserByEmail(userEmail);
        Term savedTerm = getTermByName(user, termName);
        termService.deleteTerm(user, savedTerm);
    }

    @GetMapping
    public List<TermResponse> getAllTerms(@RequestHeader("X-Auth-Mail") String userEmail) {

        return termService.getAllTerms(getUserByEmail(userEmail)).stream()
                .map(TermResponse::new).collect(Collectors.toList());
    }

    // Auxiliary

    private Term getTermByName(User user, String name) {
        Term term = termService.getTerm(user, name);
        if (term == null) {
            throw new NotFoundException("Term not found");
        }
        return term;
    }

    private User getUserByEmail(String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

}
