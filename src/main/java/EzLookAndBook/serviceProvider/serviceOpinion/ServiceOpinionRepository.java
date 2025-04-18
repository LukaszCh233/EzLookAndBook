package EzLookAndBook.serviceProvider.serviceOpinion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ServiceOpinionRepository extends JpaRepository<ServiceOpinion, Long> {
    List<ServiceOpinion> findByServiceProviderId(Long serviceProviderId);
}
