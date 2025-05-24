package BLL.Services;

import BLL.JWT.JwtService;
import BLL.Services.Requests.AccountDTO;
import BLL.Services.Requests.CreateUserRequest;
import BLL.Services.Requests.FriendAccountDTO;
import Controllers.Clients.AccountClient;
import Controllers.Clients.UserClient;
import DAL.Models.Client;
import DAL.Repositories.ClientRepository;
import Models.Account;
import Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    public CreateUserRequest IAMMUSIC(String login, String token) {
        if (Objects.equals(jwtService.extractLogin(token), login)) {
            return userClient.getUserByLogin(login, getHeaders(token));
        }
        throw new IllegalArgumentException("Invalid token");
    }

    public AccountDTO getAccount(String id, String token) {
        AccountDTO account = accountClient.getAccount(id, getHeaders(token));
        if (account.ownerLogin().equals(jwtService.extractLogin(token))) {
            return account;
        }
        throw new IllegalArgumentException("Invalid token");
    }

    public void createFriendship(String login1, String login2, String token) {
        userClient.createFriendship(login1, login2, getHeaders(token));
    }

    public void deleteFriendship(String login1, String login2, String token) {
        userClient.deleteFriendship(login1, login2, getHeaders(token));
    }

    public void deposit(String id, String token, Double amount) {
        if (clientRepository.getClientByLogin(jwtService.extractLogin(token)) != null) {
            accountClient.deposit(id, amount, getHeaders(token));
        }
    }

    public void withdraw(String id, String token, Double amount) {
        if (clientRepository.getClientByLogin(jwtService.extractLogin(token)) != null) {
            accountClient.withdraw(id, amount, getHeaders(token));
        }
    }

    public List<FriendAccountDTO> getFriendsWithAccounts(String login, String token) {
        List<CreateUserRequest> friends = userClient.getFriends(login, getHeaders(token));

        List<FriendAccountDTO> friendsDTO = new ArrayList<>();

        for (CreateUserRequest friend : friends) {
            String friend_login = friend.login();
            List<AccountDTO> accounts = accountClient.getAccounts(friend_login, getHeaders(token));
            friendsDTO.add(new FriendAccountDTO(friend_login, accounts));
        }
        return friendsDTO;
    }
}
