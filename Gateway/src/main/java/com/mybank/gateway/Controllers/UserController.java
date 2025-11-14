package com.mybank.gateway.Controllers;

import com.mybank.gateway.DTO.Gender;
import com.mybank.gateway.DTO.HairColor;
import com.mybank.gateway.DTO.UserDTO;
import com.mybank.gateway.Models.Role;
import com.mybank.gateway.Models.User;
import com.mybank.gateway.Services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Value("${bank.service.url}")
    private String bankServiceUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin/create-admin")
    public void createAdmin(@RequestParam("login") String login,
                            @RequestParam("password") String password) {
        userService.addUser(new User(login, password, Role.ADMIN));
    }

    @PostMapping("/admin/create-client")
    public ResponseEntity<UserDTO> registerUser(@RequestParam("login") String login,
                                                @RequestParam("password") String password,
                                                @RequestParam("name") String name,
                                                @RequestParam("age") int age,
                                                @RequestParam("gender") Gender gender,
                                                @RequestParam("hair_colour") HairColor hairColor) {
        userService.addUser(new User(login, password, Role.CLIENT));

        return restTemplate.exchange(
                bankServiceUrl
                        + "/users"
                        + "/register"
                        + "?login=" + login
                        + "&name=" + name
                        + "&age=" + age
                        + "&gender=" + gender
                        + "&hair_color=" + hairColor,
                HttpMethod.POST,
                null,
                UserDTO.class
        );
    }

    @GetMapping("/admin/clients/filter-by-hair_color/{hair_color}")
    public ResponseEntity<List<UserDTO>> getUsersFilteredByHairColor(
            @PathVariable("hair_color")
            HairColor hairColor
    ) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/users"
                        + "/filter-by-haircolor"
                        + "/" + hairColor,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    @GetMapping("/admin/clients/filter-by-gender/{gender}")
    public ResponseEntity<List<UserDTO>> getUsersFilteredByGender(
            @PathVariable("gender")
            Gender gender
    ) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/users"
                        + "/filter-by-gender"
                        + "/" + gender,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    @GetMapping("/admin/clients/{login}/info")
    public ResponseEntity<UserDTO> getUserInfoByLogin(
            @PathVariable("login")
            String login
    ) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/users"
                        + "/" + login
                        + "/info",
                HttpMethod.GET,
                null,
                UserDTO.class
        );
    }

    @GetMapping("/client/info")
    public ResponseEntity<UserDTO> getAuthenticatedUserInfo(
            Authentication authentication
    ) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/users"
                        + "/" + authentication.getName()
                        + "/info",
                HttpMethod.GET,
                null,
                UserDTO.class
        );
    }

    @PostMapping("/client/add-friend/{friend_login}")
    public ResponseEntity<String> addFriend(
            @PathVariable("friend_login")
            String friendLogin,
            Authentication authentication
    ) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/users"
                        + "/" + authentication.getName()
                        + "/add-friend"
                        + "/" + friendLogin,
                HttpMethod.POST,
                null,
                String.class
        );
    }

    @PostMapping("/client/remove-friend/{friend_login}")
    public ResponseEntity<String> removeFriend(
            @PathVariable("friend_login")
            String friendLogin,
            Authentication authentication
    ) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/users"
                        + "/" + authentication.getName()
                        + "/remove-friend"
                        + "/" + friendLogin,
                HttpMethod.POST,
                null,
                String.class
        );
    }
}
