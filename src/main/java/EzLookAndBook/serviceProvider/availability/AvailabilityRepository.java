package EzLookAndBook.serviceProvider.availability;

import EzLookAndBook.serviceProvider.availability.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    Optional<Availability> findByAvailableDate(LocalDateTime localDateTime);

    Optional<Availability> findByServiceOptionId(Long id);

    Optional<Availability> findByServiceOption_idAndAvailableDate(Long serviceOptionId, LocalDate localDate);
}
