package com.weatherapp.project.login;

import com.weatherapp.project.exceptions.IncorrectPasswordException;
import com.weatherapp.project.model.QuickStart;
import com.weatherapp.project.user.User;
import com.weatherapp.project.user.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
public class LoginService {
    private final static String INCORRECT_PASSWORD_MESSAGE = "this password is incorrect";
    private UserService userService;
    private User currentUser;

    public LoginService(QuickStart quickStart) {
        this.userService = new UserService(quickStart);
        this.currentUser = new User();
    }

    public LoginService() {
    }

    protected Boolean passwordValidation(User user, String password){
        if(user.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    public void login(LoginRequest request) throws IncorrectPasswordException {
        String currentUserName = request.username();
        currentUser = userService.loadUserByUsername(currentUserName);
        if(!passwordValidation(currentUser, request.password())){
            throw new IncorrectPasswordException(INCORRECT_PASSWORD_MESSAGE);
        }
    }
}
