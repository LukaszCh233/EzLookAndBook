package EzLookAndBook.serviceProvider.repository;

import EzLookAndBook.serviceProvider.entity.ServiceOpinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOpinionRepository extends JpaRepository<ServiceOpinion, Long> {
    List<ServiceOpinion> findByServiceProviderId(Long serviceProviderId);
}
