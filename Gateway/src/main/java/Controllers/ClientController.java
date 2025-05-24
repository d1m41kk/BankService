package Controllers;

import BLL.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/YES_I_AM/{login}")
    public ResponseEntity<?> getClient(@PathVariable("login") String login, @RequestParam("token") String token) {
        return clientService.IAMMUSIC(login, token);
    }

    @GetMapping("/my_account/{id}")
    public ResponseEntity<?> getMyAccount(@PathVariable("id") String id, @RequestParam("token") String token) {
        return clientService.getAccount(id, token);
    }

    @PostMapping("{user_login}/{friend_login}/friendship")
    public ResponseEntity<?> createFriendship(@PathVariable("user_login") String user_login,
                                              @PathVariable("friend_login") String friend_login,
                                              @RequestParam("token") String token){
        return clientService.createFriendship(user_login, friend_login, token);
    }

    @DeleteMapping("{user_login}/{friend_login}/friendship")
    public ResponseEntity<?> deleteFriendship(@PathVariable("user_login") String user_login,
                                              @PathVariable("friend_login") String friend_login,
                                              @RequestParam("token") String token){
        return clientService.deleteFriendship(user_login, friend_login, token);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable("id") String id, @RequestParam("token") String token, @RequestBody Double amount) {
        return clientService.deposit(id, token, amount);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable("id") String id, @RequestParam("token") String token, @RequestBody Double amount) {
        return clientService.withdraw(id, token, amount);
    }
}
