package Controllers.Clients;

import BLL.Services.Requests.AccountDTO;
import BLL.Services.Requests.OperationDTO;
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
public class AccountClient {
    private final RestTemplate restTemplate;
    private final static String BASE = "http://localhost:8080/api/accounts/";

    @Autowired
    public AccountClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccountDTO getAccount(String id) {
        ResponseEntity<AccountDTO> response = restTemplate.exchange(
                BASE + id,
                HttpMethod.GET,
                null,
                AccountDTO.class
        );
        return response.getBody();
    }

    public List<AccountDTO> getAccounts(String ownerLogin) {
        ResponseEntity<AccountDTO[]> response = restTemplate.exchange(
                BASE + "get_accounts_by_owner/" + ownerLogin,
                HttpMethod.GET,
                null,
                AccountDTO[].class
        );
        AccountDTO[] body = response.getBody();
        return body != null ? List.of(body) : Collections.emptyList();
    }

    public List<OperationDTO> getOperations(String id) {
        ResponseEntity<OperationDTO[]> response = restTemplate.exchange(
                "http://localhost:8080/api/operations/" + id,
                HttpMethod.GET,
                null,
                OperationDTO[].class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    public void withdraw(String id, Double amount) {
        HttpEntity<Double> request = new HttpEntity<>(amount);
        restTemplate.exchange(
                BASE + id + "/withdraw",
                HttpMethod.POST,
                request,
                Void.class
        );
    }

    public void deposit(String id, Double amount) {
        HttpEntity<Double> request = new HttpEntity<>(amount);
        restTemplate.exchange(
                BASE + id + "/deposit",
                HttpMethod.POST,
                request,
                Void.class
        );
    }
}
