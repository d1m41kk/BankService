import Entities.Models.Account;
import Entities.Models.User;
import Entities.Services.AccountService;
import Entities.Services.UserService;
import Abstractions.IAccountRepository;
import Abstractions.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    private IUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testAddUser() {
        User user = new User("testUser", "Test Name", true, 25, null);
        userService.AddUser(user);
        verify(userRepository).AddUser(user);
    }

    @Test
    void testGetUser_Found() {
        User user1 = new User("user1", "User One", true, 30, null);
        User user2 = new User("user2", "User Two", false, 28, null);
        when(userRepository.GetUsers()).thenReturn(Arrays.asList(user1, user2));

        User result = userService.GetUser("user1");
        assertNotNull(result);
        assertEquals("user1", result.login);
    }

    @Test
    void testGetUser_NotFound() {
        when(userRepository.GetUsers()).thenReturn(List.of());
        User result = userService.GetUser("nonexistentUser");
        assertNull(result);
    }
}

class AccountServiceTest {
    private AccountService accountService;
    private IAccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(IAccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void testAddAccount() {
        Account account = new Account("user1");
        accountService.AddAccount(account);
        verify(accountRepository).AddAccount(account);
    }

    @Test
    void testGetAccount() {
        Account account = new Account("user1");
        when(accountRepository.GetAccount("1")).thenReturn(account);

        Account result = accountService.GetAccount("1");
        assertNotNull(result);
        assertEquals("user1", result.ownerLogin);
    }

    @Test
    void testWithdraw_Success() {
        Account account = new Account("user1");
        account.id = "1";
        account.balance = 1000.0;

        when(accountRepository.GetAccount("1")).thenReturn(account);

        accountService.Withdraw("1", 500.0);

        verify(accountRepository).UpdateBalance("1", -500.0);
    }

    @Test
    void testWithdraw_Fail_NotEnoughBalance() {
        Account account = new Account("user1");
        account.id = "1";
        account.balance = 100.0;

        when(accountRepository.GetAccount("1")).thenReturn(account);

        accountService.Withdraw("1", 500.0);

        verify(accountRepository, never()).UpdateBalance(anyString(), anyDouble());
    }

    @Test
    void testDeposit() {
        Account account = new Account("user1");
        account.id = "1";
        account.balance = 1000.0;

        when(accountRepository.GetAccount("1")).thenReturn(account);

        accountService.Deposit("1", 500.0);

        verify(accountRepository).UpdateBalance("1", 500.0);
    }
}
