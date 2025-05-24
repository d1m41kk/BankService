package Security.Config;

import DAL.Models.Client;
import DAL.Repositories.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ATMClientDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;

    public ATMClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Client> client = Optional.ofNullable(clientRepository.getClientByLogin(login));

        return client.map(ATMClientDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}