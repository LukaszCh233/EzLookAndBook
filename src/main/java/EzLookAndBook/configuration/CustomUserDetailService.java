package EzLookAndBook.configuration;

import EzLookAndBook.account.admin.Admin;
import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.admin.AdminRepository;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.account.owner.OwnerRepository;
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
    private final AdminRepository adminRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> userOptional = clientRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        Optional<Owner> ownerOptional = ownerRepository.findByEmail(email);
        if (ownerOptional.isPresent()) {
            return ownerOptional.get();
        }
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            return adminOptional.get();
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
