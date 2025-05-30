package BLL.Services;

import BLL.JWT.JwtService;
import BLL.Services.Requests.AccountDTO;
import BLL.Services.Requests.CreateUserRequest;
import BLL.Services.Requests.FriendAccountDTO;
import Controllers.Clients.AccountClient;
import Controllers.Clients.UserClient;
import DAL.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final AccountClient accountClient;
    private final UserClient userClient;

    @Autowired
    public ClientService(RestTemplate restTemplate, ClientRepository clientRepository, JwtService jwtService, AccountClient accountClient, UserClient userClient) {
        this.clientRepository = clientRepository;
        this.jwtService = jwtService;
        this.accountClient = accountClient;
        this.userClient = userClient;
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public CreateUserRequest IAMMUSIC(String login) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentLogin.equals(login)) {
            return userClient.getUserByLogin(login);
        }
        throw new IllegalArgumentException("Invalid token");
    }

    public AccountDTO getAccount(String id) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountClient.getAccount(id);
        if (account.ownerLogin().equals(currentLogin)) {
            return account;
        }
        throw new IllegalArgumentException("Invalid token");
    }

    public void createFriendship(String login1, String login2) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentLogin.equals(login1)) {
            userClient.createFriendship(login1, login2);
        }
        else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    public void deleteFriendship(String login1, String login2) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentLogin.equals(login1)) {
            userClient.deleteFriendship(login1, login2);
        }
        else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    public void deposit(String id, Double amount) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (clientRepository.getClientByLogin(currentLogin) != null) {
            accountClient.deposit(id, amount);
        }
    }

    public void withdraw(String id, Double amount) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (clientRepository.getClientByLogin(currentLogin) != null) {
            accountClient.withdraw(id, amount);
        }
    }

    public List<FriendAccountDTO> getFriendsWithAccounts() {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        List<CreateUserRequest> friends = userClient.getFriends(currentLogin);
        List<FriendAccountDTO> friendsDTO = new ArrayList<>();

        for (CreateUserRequest friend : friends) {
            String friend_login = friend.login();
            List<AccountDTO> accounts = accountClient.getAccounts(friend_login);
            friendsDTO.add(new FriendAccountDTO(friend_login, accounts));
        }
        return friendsDTO;
    }
}
