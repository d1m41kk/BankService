package Security.Config;

import BLL.JWT.JwtFilter;
import DAL.Repositories.AdminRepository;
import DAL.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final JwtFilter jwtFilter;
    @Autowired
    public SecurityConfig(AdminRepository adminRepository, ClientRepository clientRepository, JwtFilter jwtFilter) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public UserDetailsService clientDetailsService() {
        return new ATMClientDetailsService(clientRepository);
    }

    @Bean
    public UserDetailsService adminDetailsService() {
        return new ATMAdminDetailsService(adminRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProviderForClient() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(clientDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationProvider authenticationProviderForAdmin() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(adminDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/create_user").hasRole("ADMIN")
                        .requestMatchers("/api/users/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/users/register").permitAll()
                        .requestMatchers("/api/users/filter").hasAnyRole("ADMIN")
                        .requestMatchers("/api/users/*/*/friendship").hasRole("CLIENT")
                        .requestMatchers("/api/users/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/accounts/get_cur").permitAll()
                        .requestMatchers("/api/accounts/**").authenticated()
                        .anyRequest().permitAll()
                )
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID")
                        .logoutUrl("api/auth/logout")
                        .logoutSuccessUrl("/")
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(authenticationProviderForClient());
        builder.authenticationProvider(authenticationProviderForAdmin());
        return builder.build();
    }
}
