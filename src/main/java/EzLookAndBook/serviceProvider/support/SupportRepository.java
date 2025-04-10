package EzLookAndBook.serviceProvider.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SupportRepository extends JpaRepository<SupportChat, Long> {
}
