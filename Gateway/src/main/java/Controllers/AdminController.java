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
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void createUser(@RequestBody CreateUserRequest createUserRequest) {
        adminService.createClient(createUserRequest.login(),
                createUserRequest.password(),
                createUserRequest.name(),
                createUserRequest.sex(),
                createUserRequest.age(),
                createUserRequest.hairColor());
    }

    @GetMapping("/filter_users")
    public List<CreateUserRequest> filterUsers(@RequestParam("hairColor") HairColor hairColor, @RequestParam("sex") Boolean sex) {
        return adminService.getUsersByHairColorAndSex(hairColor, sex);
    }

    @GetMapping("/get_user/{login}")
    public CreateUserRequest getUser(@PathVariable("login") String login) {
        return adminService.getClientByLogin(login);
    }

    @GetMapping("/get_account/{login}")
    public List<AccountDTO> getAccount(@PathVariable("login") String login) {
        return adminService.getAccountByUsersLogin(login);
    }

    @GetMapping("/get_operations_of_account/{id}")
    public List<OperationDTO> getOperationsOfAccount(@PathVariable("id") String id) {
        return adminService.getOperationsByAccountId(id);
    }
}