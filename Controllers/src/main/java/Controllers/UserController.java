package Controllers;

import DTO.CreateUserDTO;
import Entities.Services.UserService;
import Enums.HairColor;
import Models.User;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешный ответ"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера")
})
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable("login") String login) {
        User user = userService.getUserByLogin(login);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @PostMapping("/register")
    public void createUser(@RequestBody CreateUserDTO request) {
        userService.addUser(
                request.login,
                request.name,
                request.sex,
                request.age,
                request.hairColor
        );
    }
    @GetMapping("/filter")
    public List<User> getUsersByHairColorAndSex(@RequestParam("hair_color") HairColor hairColor, @RequestParam("sex") Boolean sex){
        return userService.getUsersByHairColorAndSex(hairColor, sex);
    }
    @PostMapping("/{user_login}/{friend_login}/friendship")
    public void createFriendship(@PathVariable("user_login") String user_login, @PathVariable("friend_login") String friend_login) {
        userService.createFriendship(user_login, friend_login);
    }
    @DeleteMapping("/admin/{login}")
    public void deleteUser(@PathVariable("login") String login) {
        userService.deleteUsersByLogin(login);
    }
    @DeleteMapping("/{user_login}/{friend_login}/friendship")
    public void deleteFriendship(@PathVariable("user_login") String user_login, @PathVariable("friend_login") String friend_login) {
        userService.deleteFriendship(user_login, friend_login);
    }

    @GetMapping("/me")
    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
