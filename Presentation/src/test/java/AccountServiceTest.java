import Entities.Models.Account;
import Entities.Services.AccountService;
import Abstractions.IAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    private AccountService accountService;
    private IAccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(IAccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void testWithdraw_Success() {
        Account account = new Account(1,"TestUser");
        account.Balance = 1000.0;

        when(accountRepository.GetAccount(1)).thenReturn(account);

        accountService.Withdraw(1, 500.0);

        assertEquals(500.0, account.Balance);
    }

    @Test
    void testWithdraw_Fail_NotEnoughBalance() {
        Account account = new Account(1,"TestUser");
        account.Balance = 100.0;

        when(accountRepository.GetAccount(1)).thenReturn(account);

        accountService.Withdraw(1, 500.0);

        assertEquals(100.0, account.Balance);
    }

    @Test
    void testDeposit_Success() {

        Account account = new Account(1,"TestUser");
        account.Balance = 1000.0;

        when(accountRepository.GetAccount(1)).thenReturn(account);

        accountService.Deposit(1, 500.0);

        assertEquals(1500.0, account.Balance);
    }
}
