package BLL.Services;

import BLL.JWT.JwtService;
import DAL.Models.Client;
import DAL.Repositories.AdminRepository;
import DAL.Repositories.ClientRepository;
import Enums.HairColor;
import Models.Account;
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

    @Autowired
    public AdminService(RestTemplate restTemplate, ClientRepository clientRepository, AdminRepository adminRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public ResponseEntity<?> createClient(String login, String password, String name, Boolean sex, Integer age, HairColor hairColor, String token) {
        Client client = new Client();
        client.setLogin(login);
        client.setPassword(passwordEncoder.encode(password));
        client.setRole("ROLE_CLIENT");
        User user = new User();
        user.setLogin(login);
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        user.setHairColor(hairColor);
        if (clientRepository.getClientByLogin(login) == null && adminRepository.getAdminByLogin(jwtService.extractLogin(token)) != null) {
            clientRepository.save(client);
            HttpEntity<User> entity = new HttpEntity<>(user, getHeaders(token));
            restTemplate.exchange(BASE + "/users/register", HttpMethod.POST, entity, User.class );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    public ResponseEntity<List<User>> getUsersByHairColorAndSex(HairColor hairColor, Boolean sex, String token) {
        String url = BASE + "/users/filter?hair_color=" + hairColor.name() + "&sex=" + sex;
        HttpEntity<Void> entity = new HttpEntity<>(getHeaders(token));

        try {
            String login = jwtService.extractLogin(token);

            if (adminRepository.getAdminByLogin(login) != null) {
                ResponseEntity<User[]> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        User[].class
                );

                List<User> users = response.getBody() != null
                        ? List.of(response.getBody())
                        : new ArrayList<>();

                return ResponseEntity.ok(users);
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<User> getClientByLogin(String login, String token) {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders(token));
        try {
            if (adminRepository.getAdminByLogin(jwtService.extractLogin(token)) != null) {
                return restTemplate.exchange(BASE + "/users/" + login, HttpMethod.GET, entity, User.class);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<Account> getAccountByUsersLogin(String login, String token) {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders(token));
        String url = BASE + "/accounts/" + login;
        try {
            if (adminRepository.getAdminByLogin(jwtService.extractLogin(token)) != null) {
                return restTemplate.exchange(url, HttpMethod.GET, entity, Account.class);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    public ResponseEntity<List<Operation>> getOperationsByAccountId(String id, String token) {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders(token));
        String url = BASE + "/operations/" + id;
        try {
            if (adminRepository.getAdminByLogin(jwtService.extractLogin(token)) != null) {
                ResponseEntity<Operation[]> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        Operation[].class
                );

                List<Operation> operations = response.getBody() != null
                        ? List.of(response.getBody())
                        : new ArrayList<>();

                return ResponseEntity.ok(operations);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
