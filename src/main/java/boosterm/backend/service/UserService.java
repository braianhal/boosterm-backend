package boosterm.backend.service;

import boosterm.backend.domain.User;
import boosterm.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepo repo;

    public void createUser(String email) {
        User user = new User(email);
        repo.save(user);
    }

    public User getUser(String email) {
        return repo.get(email);
    }

}
