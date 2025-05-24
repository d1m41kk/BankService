package BLL.Services;

import BLL.JWT.JwtService;
import BLL.Services.Requests.AccountDTO;
import BLL.Services.Requests.CreateUserRequest;
import DAL.Models.Client;
import DAL.Repositories.ClientRepository;
import Models.Account;
import Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ClientService {
    private final RestTemplate restTemplate;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private static final String BASE = "http://localhost:8080/api";

    @Autowired
    public ClientService(RestTemplate restTemplate, ClientRepository clientRepository, JwtService jwtService) {
        this.restTemplate = restTemplate;
        this.clientRepository = clientRepository;
        this.jwtService = jwtService;
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public ResponseEntity<CreateUserRequest> IAMMUSIC(String login, String token) {
        HttpEntity<CreateUserRequest> entity = new HttpEntity<>(getHeaders(token));
        try {
            if (Objects.equals(jwtService.extractLogin(token), login)) {
                return restTemplate.exchange(BASE + "/users/" + login, HttpMethod.GET, entity, CreateUserRequest.class);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    public ResponseEntity<AccountDTO> getAccount(String id, String token) {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders(token));
        String url = BASE + "/accounts/" + id;
        try {
            AccountDTO account = restTemplate.exchange(url, HttpMethod.GET, entity, AccountDTO.class).getBody();
            if (account != null && account.ownerLogin().equals(jwtService.extractLogin(token))) {
                return ResponseEntity.ok(account);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    public ResponseEntity<?> createFriendship(String login1, String login2, String token) {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders(token));
        String url = BASE + "/users/" + login1 + "/" + login2 + "/friendship";
        try {
            return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    public ResponseEntity<?> deleteFriendship(String login1, String login2, String token) {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders(token));
        String url = BASE + "/users/" + login1 + "/" + login2 + "/friendship";
        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    public ResponseEntity<?> deposit(String id, String token, Double amount) {
        HttpEntity<Double> entity = new HttpEntity<>(amount, getHeaders(token));
        String url = BASE + "/accounts/" + id + "/deposit";
        try {
            return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    public ResponseEntity<?> withdraw(String id, String token, Double amount) {
        HttpEntity<Double> entity = new HttpEntity<>(amount, getHeaders(token));
        String url = BASE + "/accounts/" + id + "/withdraw";
        try {
            return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
