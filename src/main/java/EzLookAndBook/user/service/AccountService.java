package EzLookAndBook.user.service;

import EzLookAndBook.configuration.JwtService;
import EzLookAndBook.exception.ExistsException;
import EzLookAndBook.exception.UnauthorizedOperationException;
import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.user.Role;
import EzLookAndBook.user.dto.AdminDTO;
import EzLookAndBook.user.dto.ClientDTO;
import EzLookAndBook.user.dto.OwnerDTO;
import EzLookAndBook.user.entity.*;
import EzLookAndBook.user.repository.AdminRepository;
import EzLookAndBook.user.repository.ClientRepository;
import EzLookAndBook.user.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final OwnerRepository ownerRepository;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper entityMapper;
    private final JwtService jwtService;

    public AccountService(OwnerRepository ownerRepository, ClientRepository clientRepository,AdminRepository adminRepository,
                          PasswordEncoder passwordEncoder, EntityMapper entityMapper, JwtService jwtService) {
        this.ownerRepository = ownerRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityMapper = entityMapper;
        this.jwtService = jwtService;
    }

    public OwnerDTO createOwner(OwnerRegister ownerRegister) {
        if (ownerRepository.findByEmail(ownerRegister.getEmail()).isPresent()) {
            throw new ExistsException("User with this email exist");
        }
        Owner owner = new Owner();
        owner.setName(ownerRegister.getName());
        owner.setLastName(ownerRegister.getLastName());
        owner.setEmail(ownerRegister.getEmail());
        owner.setPassword(ownerRegister.getPassword());
        owner.setRole(Role.OWNER);
        String encodedPassword = passwordEncoder.encode(owner.getPassword());
        owner.setPassword(encodedPassword);

        return entityMapper.mapOwnerToOwnerDTO(ownerRepository.save(owner));
    }

    public ClientDTO createClient(Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new ExistsException("User with this email exist");
        }
        client.setRole(Role.CLIENT);
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);

        return entityMapper.mapClientToClientDTO(clientRepository.save(client));
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

    public String ownerAuthorization(LoginRequest loginRequest) {
        Owner registeredOwner = ownerRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("Owner not exist"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), registeredOwner.getPassword())) {

            throw new UnauthorizedOperationException("Incorrect password or email");
        }
        return jwtService.generateToken(registeredOwner);
    }

    public String clientAuthorization(LoginRequest loginRequest) {
        Client registeredClient = clientRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("Client not exist"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), registeredClient.getPassword())) {
            throw new UnauthorizedOperationException("Incorrect password or email");
        }
        return jwtService.generateToken(registeredClient);
    }
}
