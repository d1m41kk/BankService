package Controllers;

import Entities.Services.UserService;
import Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/get_by_login/{login}")
    public User getUserByLogin(@PathVariable("login") String login) {
        return userService.getUserByLogin(login);
    }
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @PostMapping("/create_user")
    public void createUser(@RequestBody User user) {
        userService.addUser(user);
    }
}
