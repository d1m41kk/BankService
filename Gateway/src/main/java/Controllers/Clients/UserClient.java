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

    public void createUser(CreateUserRequest createUserRequest, HttpHeaders headers) {
        HttpEntity<CreateUserRequest> request = new HttpEntity<>(createUserRequest, headers);
        restTemplate.exchange(
                BASE + "register",
                HttpMethod.POST,
                request,
                CreateUserRequest.class
        );
    }

    public CreateUserRequest getUserByLogin(String login, HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<CreateUserRequest> response = restTemplate.exchange(
                BASE + login,
                HttpMethod.GET,
                entity,
                CreateUserRequest.class
        );
        return response.getBody();
    }

    public void createFriendship(String login1, String login2, HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.exchange(
                BASE + login1 + "/" + login2 + "/friendship",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

    public void deleteFriendship(String login1, String login2, HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.exchange(
                BASE + login1 + "/" + login2 + "/friendship",
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }

    public List<CreateUserRequest> getUsersByHairColorAndSex(HairColor hairColor, Boolean sex, HttpHeaders httpHeaders){
        HttpEntity<CreateUserRequest[]> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CreateUserRequest[]> response = restTemplate.exchange(
                BASE + "filter?hair_color=" + hairColor.name() + "&sex=" + sex,
                HttpMethod.GET,
                entity,
                CreateUserRequest[].class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    public List<CreateUserRequest> getFriends(String login, HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<CreateUserRequest[]> response = restTemplate.exchange(
                BASE + "friends/" + login,
                HttpMethod.GET,
                entity,
                CreateUserRequest[].class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }
}
