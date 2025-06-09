package Security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CombinedUserDetailsService implements UserDetailsService {
    private final ATMAdminDetailsService adminDetailsService;
    private final ATMClientDetailsService clientDetailsService;

    @Autowired
    public CombinedUserDetailsService(ATMAdminDetailsService adminDetailsService, ATMClientDetailsService clientDetailsService) {
        this.adminDetailsService = adminDetailsService;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return adminDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException _) {
        }

        try {
            return clientDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found in admin or client services");
        }
    }
}
