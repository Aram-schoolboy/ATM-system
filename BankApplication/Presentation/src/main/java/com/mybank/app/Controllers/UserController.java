package com.mybank.app.Controllers;

import Contracts.UserService;
import Models.Gender;
import Models.HairColor;
import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybank.app.DTO.UserDTO;
import com.mybank.app.Mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add friend")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friend added"),
            @ApiResponse(responseCode = "400", description = "Can't be friends with yourself"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "User already in friend-list")
    })
    @PostMapping("/{login}/add-friend/{friendLogin}")
    public ResponseEntity<String> addFriend(
            @Parameter(description = "Login of the user who adds a friend")
            @PathVariable("login") String login,
            @Parameter(description = "Login of the user to add as friend")
            @PathVariable("friendLogin") String friendLogin) {
        userService.userLogin(login);
        userService.addFriend(friendLogin);
        return ResponseEntity.ok("Friend added");
    }

    @Operation(summary = "Remove friend")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friend removed"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "User not in friend-list")
    })
    @PostMapping("/{login}/remove-friend/{friendLogin}")
    public ResponseEntity<String> removeFriend(
            @Parameter(description = "Login of the user who removes a friend")
            @PathVariable("login") String login,
            @Parameter(description = "Login of the user to remove as friend")
            @PathVariable("friendLogin") String friendLogin) {
        userService.userLogin(login);
        userService.removeFriend(friendLogin);
        return ResponseEntity.ok("Friend removed");
    }

    @Operation(summary = "Show user info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info sent"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{login}/info")
    public ResponseEntity<UserDTO> getUserInfo(
            @Parameter(description = "Login of the user")
            @PathVariable("login") String login) {
        userService.userLogin(login);
        return ResponseEntity.ok(UserMapper.toDTO(userService.getUser()));
    }

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info sent"),
            @ApiResponse(responseCode = "409", description = "Login already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestParam("login") String login,
                                                @RequestParam("name") String name,
                                                @RequestParam("age") int age,
                                                @RequestParam("gender") Gender gender,
                                                @RequestParam("hair_color") HairColor hairColor) {
        User user = userService.createUser(login, name, age, gender, hairColor);

        return new ResponseEntity<>(UserMapper.toDTO(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Show user friends")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User friends provided"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{login}/friends")
    public ResponseEntity<List<UserDTO>> getUserFriends(
            @Parameter(description = "Login of the user")
            @PathVariable("login") String login) {
        userService.userLogin(login);
        return ResponseEntity.ok(userService.getFriends().stream()
                .map(UserMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Show users filtered by hair color ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users filtered by hair color provided")
    })
    @GetMapping("/filter-by-haircolor/{hairColor}")
    public ResponseEntity<List<UserDTO>> getUsersByHairColor(
            @Parameter(description = "hair color")
            @PathVariable("hairColor") HairColor hairColor) {
        return ResponseEntity.ok(userService.getAllUsersByHairColor(hairColor).stream()
                .map(UserMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Show users filtered by gender ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User filtered by gender provided")
    })
    @GetMapping("/filter-by-gender/{gender}")
    public ResponseEntity<List<UserDTO>> getUsersByGender(
            @Parameter(description = "Login of the user")
            @PathVariable("gender") Gender gender) {
        return ResponseEntity.ok(userService.getAllUsersByGender(gender).stream()
                .map(UserMapper::toDTO)
                .toList());
    }
}
