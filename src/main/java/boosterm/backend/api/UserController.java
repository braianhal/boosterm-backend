package boosterm.backend.api;

import boosterm.backend.api.exception.NotFoundExceptionResponse;
import boosterm.backend.api.response.UserResponse;
import boosterm.backend.domain.Term;
import boosterm.backend.domain.User;
import boosterm.backend.service.TermService;
import boosterm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public TermService termService;

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    public void createUser(@RequestHeader("X-Auth-Mail") String email) {
        userService.createUser(email);
    }

    @GetMapping
    public UserResponse getUser(@RequestHeader("X-Auth-Mail") String email) {
        return new UserResponse(getUserByEmail(email));
    }

    @GetMapping("/config")
    public Map<String, Boolean> getGraphConfigResponse(@RequestHeader("X-Auth-Mail") String userEmail,
                                                       @RequestParam("code") String termCode) {
        User user = getUserByEmail(userEmail);
        Term term = getTermByCode(user, termCode);
        return userService.getGraphsConfig(user, term);
    }

    @PutMapping("/config")
    @ResponseStatus(NO_CONTENT)
    public void saveGraphConfig(@RequestHeader("X-Auth-Mail") String userEmail,
                                @RequestParam("code") String termCode,
                                @RequestBody Map<String, Boolean> config) {
        User user = getUserByEmail(userEmail);
        Term term = getTermByCode(user, termCode);
        userService.saveGraphsConfig(user, term, config);
    }

    // Auxiliary

    private User getUserByEmail(String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new NotFoundExceptionResponse("User not found");
        }
        return user;
    }

    private Term getTermByCode(User user, String code) {
        Term term = termService.getTerm(user, code);
        if (term == null) {
            throw new NotFoundExceptionResponse("Term not found");
        }
        return term;
    }

}
