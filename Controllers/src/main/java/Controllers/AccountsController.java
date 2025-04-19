package Controllers;

import Entities.Services.AccountService;
import Models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    Account getAccount(@PathVariable("id") String id) {
        return accountService.getAccount(id);
    }

    @PostMapping
    void addAccount(@RequestBody Account account) {
        accountService.addAccount(account);
    }

    @PostMapping("/withdraw/{id}")
    void withdraw(@PathVariable("id") String id, @RequestBody Double amount) {
        accountService.withdraw(id, amount);
    }

    @PostMapping("/deposit/{id}")
    void deposit(@PathVariable("id") String id, @RequestBody Double amount) {
        accountService.Deposit(id, amount);
    }

    @GetMapping("/{id}/balance")
    Double getBalance(@PathVariable("id") String id) {
        return accountService.getBalance(id);
    }
}
