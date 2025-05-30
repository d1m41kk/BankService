package Security.Config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface ATMEntityDetails {
    String getUsername();
    String getRole();
    Collection<? extends GrantedAuthority> getAuthorities();
}
