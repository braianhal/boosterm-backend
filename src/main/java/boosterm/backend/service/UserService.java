package boosterm.backend.service;

import boosterm.backend.api.request.UserRequest;
import boosterm.backend.domain.User;
import boosterm.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepo repo;

    public User createUser(UserRequest userData) {
        User user = new User(userData.getEmail(), userData.getFirstName(), userData.getLastName());
        repo.save(user);
        return user;
    }

    public User getUser(String email) {
        return repo.get(email);
    }

}
