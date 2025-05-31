package Service;

import DTO.AccountEvent;
import DTO.UserEvent;
import Repositories.AccountEventRepository;
import Repositories.UserEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListeners {
    private final UserEventRepository clientRepo;
    private final AccountEventRepository accountRepo;

    public KafkaListeners(UserEventRepository clientRepo, AccountEventRepository accountRepo) {
        this.clientRepo = clientRepo;
        this.accountRepo = accountRepo;
    }

    @KafkaListener(topics = "client-topic", groupId = "storage-group")
    public void listenClient(String key, String message) {
        clientRepo.save(new UserEvent(key, message));
    }

    @KafkaListener(topics = "account-topic", groupId = "storage-group")
    public void listenAccount(String key, String message) {
        accountRepo.save(new AccountEvent(key, message));
    }
}
