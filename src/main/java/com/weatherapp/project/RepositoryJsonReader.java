package com.weatherapp.project;

import com.weatherapp.project.model.QuickStart;
import com.weatherapp.project.user.User;
import com.weatherapp.project.user.UserRole;
import org.json.JSONObject;

public class RepositoryJsonReader {
    private QuickStart quickStart;
    private String jsonFromRepository;

    public RepositoryJsonReader(QuickStart quickStart) {
        this.quickStart = quickStart;
        this.jsonFromRepository = null;
    }

    public User getUserFromRepository(String userName) {
        jsonFromRepository = quickStart.getClientRecordByLogin(userName);
        if(jsonFromRepository != null) {
            JSONObject json = new JSONObject(jsonFromRepository);
            String name = json.getString("name");
            String username = json.getString("username");
            String email = json.getString("email");
            String password = json.getString("password");
            UserRole userRole = UserRole.valueOf(json.getString("userRole"));

            User user = new User(name, username, email, password, userRole);
            return user;
        }
        return null;
    }
}
