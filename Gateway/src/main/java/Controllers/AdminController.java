package Controllers;

import BLL.Services.AdminService;
import BLL.Services.Enums.HairColor;
import BLL.Services.Requests.AccountDTO;
import BLL.Services.Requests.CreateUserRequest;
import BLL.Services.Requests.OperationDTO;
import Models.Account;
import Models.Operation;
import Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest,
                                        @RequestParam("token") String token) {
        return adminService.createClient(createUserRequest.login(),
                createUserRequest.password(),
                createUserRequest.name(),
                createUserRequest.sex(),
                createUserRequest.age(),
                createUserRequest.hairColor(),
                token);
    }

    @GetMapping("/filter_users")
    public ResponseEntity<List<CreateUserRequest>> filterUsers(@RequestParam("hairColor") HairColor hairColor, @RequestParam("sex") Boolean sex, @RequestParam("token") String token) {
        return adminService.getUsersByHairColorAndSex(hairColor, sex, token);
    }

    @GetMapping("/get_user/{login}")
    public ResponseEntity<CreateUserRequest> getUser(@PathVariable("login") String login, @RequestParam("token") String token) {
        return adminService.getClientByLogin(login, token);
    }

    @GetMapping("/get_account/{login}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("login") String login, @RequestParam("token") String token) {
        return adminService.getAccountByUsersLogin(login, token);
    }

    @GetMapping("/get_operations_of_account/{id}")
    public ResponseEntity<List<OperationDTO>> getOperationsOfAccount(@PathVariable("id") String id, @RequestParam("token") String token) {
        return adminService.getOperationsByAccountId(id, token);
    }
}