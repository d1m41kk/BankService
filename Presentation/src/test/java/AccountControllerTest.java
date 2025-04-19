import App.Application;
import Entities.Services.AccountService;
import Models.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.id = UUID.randomUUID().toString();
        account.ownerLogin = "eblan";
        account.balance = 100;
    }

    @Test
    public void getAccount() throws Exception {
        String id = account.id;
        Mockito.when(accountService.getAccount(id)).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(account.id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ownerLogin").value(account.ownerLogin))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(account.balance));
    }

    @Test
    public void withdrawAccount() throws Exception {
        String id = account.id;
        double amount = 50.0;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/withdraw/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> amountCaptor = ArgumentCaptor.forClass(Double.class);

        verify(accountService).withdraw(idCaptor.capture(), amountCaptor.capture());

        assertEquals(id, idCaptor.getValue());
        assertEquals(amount, amountCaptor.getValue());
    }
}
