package boosterm.backend.api;

import boosterm.backend.api.exception.NotFoundExceptionResponse;
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

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    public void updateTerm(@RequestHeader("X-Auth-Mail") String userEmail,
                           @RequestParam("code") String termCode,
                           @RequestBody TermUpdateRequest req) {
        User user = getUserByEmail(userEmail);
        Term savedTerm = getTermByCode(user, termCode);
        termService.updateTerm(user, savedTerm, req);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void updateTerm(@RequestHeader("X-Auth-Mail") String userEmail,
                           @RequestParam("code") String termCode) {
        User user = getUserByEmail(userEmail);
        Term savedTerm = getTermByCode(user, termCode);
        termService.deleteTerm(user, savedTerm);
    }

    @GetMapping
    public List<TermResponse> getAllTerms(@RequestHeader("X-Auth-Mail") String userEmail) {
        return termService.getAllTerms(getUserByEmail(userEmail)).stream()
                .map(TermResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/term")
    public TermResponse getTerm(@RequestHeader("X-Auth-Mail") String userEmail,
                                @RequestParam("code") String termCode) {
        User user = getUserByEmail(userEmail);
        Term term = getTermByCode(user, termCode);
        return new TermResponse(term);
    }

    // Auxiliary

    private Term getTermByCode(User user, String code) {
        Term term = termService.getTerm(user, code);
        if (term == null) {
            throw new NotFoundExceptionResponse("Term not found");
        }
        return term;
    }

    private User getUserByEmail(String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new NotFoundExceptionResponse("User not found");
        }
        return user;
    }

}
