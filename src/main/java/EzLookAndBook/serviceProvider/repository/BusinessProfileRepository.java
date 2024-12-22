package EzLookAndBook.serviceProvider.repository;

import EzLookAndBook.serviceProvider.entity.BusinessProfile;
import EzLookAndBook.user.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {

    List<BusinessProfile> findByStatus(VerificationStatus verificationStatus);

    Optional<BusinessProfile> findByOwnerEmail(String email);
}
