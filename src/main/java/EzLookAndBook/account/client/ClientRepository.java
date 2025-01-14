package EzLookAndBook.account.client;

import EzLookAndBook.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    List<Client> findByRole(Role role);
}