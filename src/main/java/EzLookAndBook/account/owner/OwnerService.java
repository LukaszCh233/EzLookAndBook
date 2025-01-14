package EzLookAndBook.account.owner;

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
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper entityMapper;
    private final JwtService jwtService;

    public OwnerService(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder, EntityMapper entityMapper,
                        JwtService jwtService) {
        this.ownerRepository = ownerRepository;
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

    public String ownerAuthorization(LoginRequest loginRequest) {
        Owner registeredOwner = ownerRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("Owner not exist"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), registeredOwner.getPassword())) {

            throw new UnauthorizedOperationException("Incorrect password or email");
        }
        return jwtService.generateToken(registeredOwner);
    }
}
