package EzLookAndBook.account.admin;

import EzLookAndBook.account.Role;
import EzLookAndBook.account.input.LoginRequest;
import EzLookAndBook.configuration.JwtService;
import EzLookAndBook.exception.ExistsException;
import EzLookAndBook.exception.UnauthorizedOperationException;
import EzLookAndBook.mapper.EntityMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper entityMapper;
    private final JwtService jwtService;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, EntityMapper entityMapper,
                        JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityMapper = entityMapper;
        this.jwtService = jwtService;
    }

    public AdminDTO createAdmin(Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new ExistsException("User with this email exist");
        }
        admin.setRole(Role.ADMIN);
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);

        return entityMapper.mapAdminToAdminDTO(adminRepository.save(admin));
    }

    public String adminAuthorization(LoginRequest loginRequest) {
        Admin registeredAdmin = adminRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("Admin not exist"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), registeredAdmin.getPassword())) {

            throw new UnauthorizedOperationException("Incorrect password or email");
        }
        return jwtService.generateToken(registeredAdmin);
    }
}
