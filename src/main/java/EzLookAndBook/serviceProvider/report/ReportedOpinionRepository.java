package EzLookAndBook.serviceProvider.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportedOpinionRepository extends JpaRepository<ReportedOpinion,Long> {
    List<ReportedOpinion> findByStatus(ReportStatus status);
}
