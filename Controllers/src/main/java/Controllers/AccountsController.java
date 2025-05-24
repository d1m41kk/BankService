package Controllers;

import Entities.Services.AccountService;
import Models.Account;
import Requests.CreateAccountRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешный ответ"),
        @ApiResponse(responseCode = "404", description = "Аккаунт не найден"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера")
})
public class AccountsController {
    private final AccountService accountService;
    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") String id) {
        Account account= accountService.getAccount(id);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest account) {
        accountService.createAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/withdraw")
    void withdraw(@PathVariable("id") String id, @RequestBody Double amount) {
        accountService.withdraw(id, amount);
    }

    @PostMapping("/{id}/deposit")
    void deposit(@PathVariable("id") String id, @RequestBody Double amount) {
        accountService.deposit(id, amount);
    }

    @GetMapping("/{id}/balance")
    Double getBalance(@PathVariable("id") String id) {
        return accountService.getBalance(id);
    }

    @GetMapping("/get_accounts_by_owner/{login}")
    List<Account> getAccountsByOwner(@PathVariable("login") String ownerLogin) {
        return accountService.getAccounts(ownerLogin);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") String id) {
        accountService.deleteAccountById(id);
    }
}
