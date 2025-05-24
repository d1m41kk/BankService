package Security.Config;

import DAL.Models.Admin;
import DAL.Repositories.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ATMAdminDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    public ATMAdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Admin> admin = Optional.ofNullable(adminRepository.getAdminByLogin(login));

        return admin.map(ATMAdminDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Админ не найден"));
    }
}
