package boosterm.backend.api;

import boosterm.backend.api.exception.NotFoundException;
import boosterm.backend.api.response.UserResponse;
import boosterm.backend.domain.User;
import boosterm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    public void createUser(@RequestHeader("X-Auth-Mail") String email) {
        userService.createUser(email);
    }

    @GetMapping
    public UserResponse getUser(@RequestHeader("X-Auth-Mail") String email) {
        return new UserResponse(getUserByEmail(email));
    }

    // Auxiliary

    private User getUserByEmail(String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

}
