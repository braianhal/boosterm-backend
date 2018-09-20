package boosterm.backend.api.response;

import boosterm.backend.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class UserResponse {

    private String email;

    public UserResponse(User user) {
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }

}
