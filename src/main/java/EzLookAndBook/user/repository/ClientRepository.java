package EzLookAndBook.user.repository;

import EzLookAndBook.user.Role;
import EzLookAndBook.user.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    List<Client> findByRole(Role role);
}