package EzLookAndBook.serviceProvider.availability;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByServiceOptionId(Long id);

    Optional<Availability> findByServiceOption_idAndAvailableDate(Long serviceOptionId, LocalDate localDate);
}
