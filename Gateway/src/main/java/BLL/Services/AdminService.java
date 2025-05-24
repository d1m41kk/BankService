package BLL.Services;

import BLL.JWT.JwtService;
import BLL.Services.Enums.HairColor;
import BLL.Services.Requests.AccountDTO;
import BLL.Services.Requests.CreateUserRequest;
import BLL.Services.Requests.OperationDTO;
import Controllers.Clients.AccountClient;
import Controllers.Clients.UserClient;
import DAL.Models.Client;
import DAL.Repositories.AdminRepository;
import DAL.Repositories.ClientRepository;
import Models.Operation;
import Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final RestTemplate restTemplate;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private static final String BASE = "http://localhost:8080/api";
    private final AccountClient accountClient;
    private final UserClient userClient;

    @Autowired
    public AdminService(RestTemplate restTemplate, ClientRepository clientRepository, AdminRepository adminRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AccountClient accountClient, UserClient userClient) {
        this.restTemplate = restTemplate;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.accountClient = accountClient;
        this.userClient = userClient;
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public void createClient(String login, String password, String name, Boolean sex, Integer age, HairColor hairColor, String token) {
        if (clientRepository.getClientByLogin(login) != null) {
            throw new IllegalArgumentException("Client with login " + login + " already exists");
        }
        Client client = new Client();
        client.setLogin(login);
        client.setPassword(passwordEncoder.encode(password));
        client.setRole("ROLE_CLIENT");
        clientRepository.save(client);
        CreateUserRequest request = new CreateUserRequest(login, password, name, sex, age, hairColor);
        userClient.createUser(request, getHeaders(token));
    }

    public List<CreateUserRequest> getUsersByHairColorAndSex(HairColor hairColor, Boolean sex, String token) {
        if (adminRepository.getAdminByLogin(jwtService.extractLogin(token)) != null) {
            return userClient.getUsersByHairColorAndSex(hairColor, sex, getHeaders(token));
        }
        throw new IllegalArgumentException("Invalid token");
    }

    public CreateUserRequest getClientByLogin(String login, String token) {
        if (clientRepository.getClientByLogin(login) != null) {
            return userClient.getUserByLogin(login, getHeaders(token));
        }
        throw new IllegalArgumentException("Invalid token");
    }

    public AccountDTO getAccountByUsersLogin(String login, String token) {
        if (adminRepository.getAdminByLogin(jwtService.extractLogin(token)) != null) {
            return accountClient.getAccount(login, getHeaders(token));
        }
        throw new IllegalArgumentException("Invalid token");
    }

    public List<OperationDTO> getOperationsByAccountId(String id, String token) {
        if (adminRepository.getAdminByLogin(jwtService.extractLogin(token)) != null) {
            return accountClient.getOperations(id, getHeaders(token));
        }
        throw new IllegalArgumentException("Invalid token");
    }
}
