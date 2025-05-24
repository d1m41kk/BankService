package Controllers;

import BLL.Services.AdminService;
import BLL.JWT.JwtService;
import BLL.Services.Requests.AuthRequest;
import BLL.Services.Requests.CreateUserRequest;
import DAL.Models.Admin;
import DAL.Models.Client;
import DAL.Repositories.AdminRepository;
import DAL.Repositories.ClientRepository;
import Security.Config.ATMAdminDetails;
import Security.Config.ATMClientDetails;
import Security.Config.ATMEntityDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @Autowired
    AuthController(AdminService adminService,
                   JwtService jwtService,
                   RestTemplate restTemplate,
                   ClientRepository clientRepository,
                   PasswordEncoder passwordEncoder,
                   AdminRepository adminRepository) {
        this.jwtService = jwtService;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        String login = authRequest.login();
        String password = authRequest.password();

        Client client = clientRepository.getClientByLogin(login);

        ATMEntityDetails details;

        if (client != null && passwordEncoder.matches(password, client.getPassword())) {
            details = new ATMClientDetails(client);
        } else {
            Admin admin = adminRepository.getAdminByLogin(login);
            if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
                details = new ATMAdminDetails(admin);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
            }
        }

        String token = jwtService.generateToken(details);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body("Успешная аутентификация");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Успешный выход из системы");
    }
}
