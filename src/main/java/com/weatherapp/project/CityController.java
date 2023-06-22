package com.weatherapp.project;

import com.weatherapp.project.ForMapping.PresentWeatherData;
import com.weatherapp.project.ForMapping.ForecastWeatherData;
import com.weatherapp.project.login.LoginRequest;
import com.weatherapp.project.login.LoginService;
import com.weatherapp.project.model.QuickStart;
import com.weatherapp.project.registration.RegistrationRequest;
import com.weatherapp.project.registration.RegistrationService;
import com.weatherapp.project.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

/**
 * This is the main controller class for handling city-related requests and actions.
 */
@Controller
public class CityController {
    private QuickStart quickStart = new QuickStart();
    @Autowired
    private ClientService clientService;
    private LoginService loginService = new LoginService(quickStart);
    private RegistrationService registrationService = new RegistrationService(quickStart);

    /**
     * Handles the request for the start page.
     *
     * @return Redirects to the login page.
     */
    @GetMapping("/")
    public String startPage() {
        return "redirect:/login";
    }

    /**
     * Handles the request for the welcome page.
     *
     * @return The welcome page.
     */
    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }

    /**
     * Handles the request for the city form.
     *
     * @return The city form page.
     */
    @GetMapping("/city")
    public String cityForm() {
        return "city";
    }

    /**
     * Handles the request for logging out.
     *
     * @return Redirects to the login page.
     */
    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/login";
    }

    /**
     * Handles the request for the login page.
     *
     * @return The login page.
     */
    @GetMapping("/login")
    public String getLoginPage() {

        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    /**
     * Handles the login request.
     *
     * @param username            The username parameter.
     * @param password            The password parameter.
     * @param redirectAttributes  Redirect attributes for storing error messages.
     * @return Redirects to the appropriate page based on the login result.
     */
    @PostMapping("/login")
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes) {
        LoginRequest loginRequest = new LoginRequest(username, password);


        try {
            loginService.login(loginRequest);
            return "redirect:/welcome";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
             return "redirect:/login";
        }
    }

    /**
     * Handles the registration request.
     *
     * @param username            The username parameter.
     * @param password            The password parameter.
     * @param name                The name parameter.
     * @param email               The email parameter.
     * @param redirectAttributes  Redirect attributes for storing error messages.
     * @return Redirects to the appropriate page based on the registration result.
     */
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("name") String name,
                        @RequestParam("email") String email,
                        RedirectAttributes redirectAttributes) {
        RegistrationRequest registrationRequest = new RegistrationRequest(name, username, email, password);
        registrationService.getUserService().setQuickStart(quickStart);
        try {

            registrationService.register(registrationRequest);
            return "redirect:/registered";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    /**
     * Handles the request for email verification and returns the email verification page.
     */
    @GetMapping("/registered")
    public String registrationSuccessfulNotify() {
        return "successfulregistration";
    }


    /**
     * Handles the request for the user's account page and adds user information to the model.
     */
    @GetMapping("/my_account")
    public String myAccount(Model model) {
        User user = loginService.getCurrentUser();
        if (Objects.isNull(user)) {
            return "welcome";
        }
        model.addAttribute("name", user.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "account";
    }


    /**
     * Handles the request for city weather data based on the provided city name.
     * Retrieves weather data from external APIs and adds it to the model.
     */
    @GetMapping("/result")
    public String cityNameResult(@RequestParam(value = "city", defaultValue = "Warszawa") String cityName, Model model) throws IOException {
        String answerStr = clientService.cityRestCall(cityName).block();
        Gson gson = new Gson();
        com.google.gson.JsonArray jsonArray = gson.fromJson(answerStr, com.google.gson.JsonArray.class);
        if (jsonArray.size() != 0) {
            com.google.gson.JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            String longitude = jsonObject.get("lon").getAsString();
            String latitude = jsonObject.get("lat").getAsString();

            String presentAnswer = clientService.coordRestCall(longitude, latitude).block();
            String forecastAnswer = clientService.coordForescastRestCall(longitude, latitude).block();
            PresentWeatherData presentWeatherData = clientService.presentDataMapping(presentAnswer);
            ForecastWeatherData forecastWeatherData = clientService.forecastDataMapping(forecastAnswer);
            String iconCode = presentWeatherData.getWeather()[0].getIcon();
            String iconLink = "https://openweathermap.org/img/wn/" + iconCode + "@4x.png";
            System.out.println(iconLink);
            model.addAttribute("iconLink", iconLink);
            model.addAttribute("answer", presentWeatherData);
            model.addAttribute("weatherDataList", forecastWeatherData.getWeatherDataList());
            model.addAttribute("lat", latitude);
            model.addAttribute("lng", longitude);
            return "result";
        } else {
            return ("city");
        }
    }

    /**
     * Model class for a station.
     */
    @Setter
    @Getter
    public class Station{
        private String name;
    }
}

