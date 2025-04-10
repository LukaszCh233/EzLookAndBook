package EzLookAndBook.serviceProvider.serviceOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ServiceOptionRepository extends JpaRepository<ServiceOption, Long> {
    Optional<ServiceOption> findByServiceProviderIdAndId(Long id, Long serviceOptionId);

    List<ServiceOption> findByServiceProviderId(Long id);

}
