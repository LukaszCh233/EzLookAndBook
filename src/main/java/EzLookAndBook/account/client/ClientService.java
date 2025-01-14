package EzLookAndBook.account.client;

import EzLookAndBook.configuration.JwtService;
import EzLookAndBook.exception.ExistsException;
import EzLookAndBook.exception.UnauthorizedOperationException;
import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.account.Role;
import EzLookAndBook.account.input.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper entityMapper;
    private final JwtService jwtService;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, EntityMapper entityMapper,
                         JwtService jwtService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityMapper = entityMapper;
        this.jwtService = jwtService;
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

    public String clientAuthorization(LoginRequest loginRequest) {
        Client registeredClient = clientRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("Client not exist"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), registeredClient.getPassword())) {
            throw new UnauthorizedOperationException("Incorrect password or email");
        }
        return jwtService.generateToken(registeredClient);
    }
}
