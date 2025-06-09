package Controllers.Clients;

import BLL.Services.Enums.HairColor;
import BLL.Services.Requests.AccountDTO;
import BLL.Services.Requests.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserClient {
    private final RestTemplate restTemplate;
    private final static String BASE = "http://localhost:8080/api/users/";
    @Autowired
    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createUser(CreateUserRequest createUserRequest) {
        restTemplate.exchange(
                BASE + "register",
                HttpMethod.POST,
                null,
                CreateUserRequest.class
        );
    }

    public CreateUserRequest getUserByLogin(String login) {
        ResponseEntity<CreateUserRequest> response = restTemplate.exchange(
                BASE + login,
                HttpMethod.GET,
                null,
                CreateUserRequest.class
        );
        return response.getBody();
    }

    public void createFriendship(String login1, String login2) {
        restTemplate.exchange(
                BASE + login1 + "/" + login2 + "/friendship",
                HttpMethod.POST,
                null,
                Void.class
        );
    }

    public void deleteFriendship(String login1, String login2) {
        restTemplate.exchange(
                BASE + login1 + "/" + login2 + "/friendship",
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

    public List<CreateUserRequest> getUsersByHairColorAndSex(HairColor hairColor, Boolean sex){
        ResponseEntity<CreateUserRequest[]> response = restTemplate.exchange(
                BASE + "filter?hair_color=" + hairColor.name() + "&sex=" + sex,
                HttpMethod.GET,
                null,
                CreateUserRequest[].class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    public List<CreateUserRequest> getFriends(String login) {
        ResponseEntity<CreateUserRequest[]> response = restTemplate.exchange(
                BASE + "friends/" + login,
                HttpMethod.GET,
                null,
                CreateUserRequest[].class
        );
        return response.getBody() != null ? List.of(response.getBody()) : Collections.emptyList();
    }
}
