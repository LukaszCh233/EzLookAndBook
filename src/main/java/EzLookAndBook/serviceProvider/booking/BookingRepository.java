package EzLookAndBook.serviceProvider.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatusAndServiceProviderId(Status status, Long id);

    @Query("SELECT b FROM Booking b JOIN b.client c WHERE c.email = :email")
    List<Booking> findByClientEmail(@Param("email") String email);
}
