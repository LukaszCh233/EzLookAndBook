package EzLookAndBook.configuration;

import EzLookAndBook.user.entity.Client;
import EzLookAndBook.user.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> userOptional = clientRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UsernameNotFoundException("Client not found with email: " + email);
    }
}
