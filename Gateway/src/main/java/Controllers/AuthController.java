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
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    AuthController(AdminService adminService,
                   JwtService jwtService,
                   RestTemplate restTemplate,
                   ClientRepository clientRepository,
                   PasswordEncoder passwordEncoder,
                   AdminRepository adminRepository,
                   AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @Operation(tags = "auth")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.login(),
                            authRequest.password()
                    )
            );

            ATMEntityDetails userDetails = (ATMEntityDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    token,
                    userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body("Успешная аутентификация");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка аутентификации");
        }
    }

    @PostMapping("/logout")
    @Operation(tags = "auth")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Успешный выход из системы");
    }
}