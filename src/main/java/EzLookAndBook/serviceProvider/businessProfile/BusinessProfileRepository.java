package EzLookAndBook.serviceProvider.businessProfile;

import EzLookAndBook.account.owner.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {
    List<BusinessProfile> findByVerificationStatus(VerificationStatus verificationStatus);

    Optional<BusinessProfile> findByOwnerEmail(String email);

    List<BusinessProfile> findAllByOwnerEmail(String email);
}
