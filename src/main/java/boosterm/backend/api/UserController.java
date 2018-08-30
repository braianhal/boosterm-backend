package boosterm.backend.api;

import boosterm.backend.api.request.UserRequest;
import boosterm.backend.api.response.UserResponse;
import boosterm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest req) {
        return new UserResponse(userService.createUser(req));
    }

    @GetMapping("/{email}")
    public UserResponse getUser(@PathVariable String email) {
        return new UserResponse(userService.getUser(email));
    }

}
