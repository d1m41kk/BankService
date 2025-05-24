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

    public AccountDTO getAccount(String id, HttpHeaders headers) {
        HttpEntity<AccountDTO> entity = new HttpEntity<>(headers);
        ResponseEntity<AccountDTO> response = restTemplate.exchange(
                BASE + id,
                HttpMethod.GET,
                entity,
                AccountDTO.class
        );
        return response.getBody();
    }

    public List<AccountDTO> getAccounts(String ownerLogin, HttpHeaders headers) {
        HttpEntity<AccountDTO> entity = new HttpEntity<>(headers);
        ResponseEntity<AccountDTO[]> response = restTemplate.exchange(
                BASE + "get_accounts_by_owner/" + ownerLogin,
                HttpMethod.GET,
                entity,
                AccountDTO[].class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    public List<OperationDTO> getOperations(String id, HttpHeaders headers) {
        HttpEntity<OperationDTO> entity = new HttpEntity<>(headers);
        ResponseEntity<OperationDTO[]> response = restTemplate.exchange(
                BASE + id,
                HttpMethod.GET,
                entity,
                OperationDTO[].class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    public void withdraw(String id, Double amount, HttpHeaders headers) {
        HttpEntity<Double> entity = new HttpEntity<>(amount, headers);
        restTemplate.exchange(
                BASE + id + "/withdraw",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

    public void deposit(String id, Double amount, HttpHeaders headers) {
        HttpEntity<Double> entity = new HttpEntity<>(amount, headers);
        restTemplate.exchange(
                BASE + id + "/deposit",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }
}
