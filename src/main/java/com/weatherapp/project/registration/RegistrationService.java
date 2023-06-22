package com.weatherapp.project.registration;

import com.weatherapp.project.model.QuickStart;
import com.weatherapp.project.user.User;
import com.weatherapp.project.user.UserRole;
import com.weatherapp.project.user.UserService;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * This class is a service for registration of new users
 */
@Service
@Getter
public class RegistrationService {

    private UserService userService;

    public RegistrationService() {
    }

    public RegistrationService(QuickStart quickStart) {
        this.userService = new UserService(quickStart);
    }

    /**
     * This method creates new user object from request.
     * @param request
     * @return
     */
    public void register(RegistrationRequest request) {
        User user = new User(
                request.name(),
                request.userName(),
                request.email(),
                request.password(),
                UserRole.USER);
        userService.signUpUser(user);
    }
}

