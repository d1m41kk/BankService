package Controllers;

import BLL.JWT.JwtService;
import BLL.Services.ClientService;
import BLL.Services.Requests.AccountDTO;
import BLL.Services.Requests.CreateUserRequest;
import BLL.Services.Requests.FriendAccountDTO;
import DAL.Models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;
    private final JwtService jwtService;

    @Autowired
    public ClientController(ClientService clientService, JwtService jwtService) {
        this.clientService = clientService;
        this.jwtService = jwtService;
    }

    @GetMapping("/YES_I_AM/{login}")
    public ResponseEntity<CreateUserRequest> getClient(@PathVariable("login") String login, @RequestParam("token") String token) {
        CreateUserRequest user = clientService.IAMMUSIC(login, token);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/my_account/{id}")
    public ResponseEntity<AccountDTO> getMyAccount(@PathVariable("id") String id, @RequestParam("token") String token) {
        AccountDTO account = clientService.getAccount(id, token);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("{user_login}/{friend_login}/friendship")
    public void createFriendship(@PathVariable("user_login") String user_login,
                                              @PathVariable("friend_login") String friend_login,
                                              @RequestParam("token") String token){
        clientService.createFriendship(user_login, friend_login, token);
    }

    @DeleteMapping("{user_login}/{friend_login}/friendship")
    public void deleteFriendship(@PathVariable("user_login") String user_login,
                                              @PathVariable("friend_login") String friend_login,
                                              @RequestParam("token") String token){
        clientService.deleteFriendship(user_login, friend_login, token);
    }

    @PostMapping("/{id}/deposit")
    public void deposit(@PathVariable("id") String id, @RequestParam("token") String token, @RequestBody Double amount) {
        clientService.deposit(id, token, amount);
    }

    @PostMapping("/{id}/withdraw")
    public void withdraw(@PathVariable("id") String id, @RequestParam("token") String token, @RequestBody Double amount) {
        clientService.withdraw(id, token, amount);
    }

    @GetMapping("/get_friends")
    public List<FriendAccountDTO> getFriends(@RequestParam("token") String token) {
        return clientService.getFriendsWithAccounts(jwtService.extractLogin(token), token);
    }
}
