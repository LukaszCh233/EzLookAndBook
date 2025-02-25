package EzLookAndBook.serviceProvider.report;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileRepository;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinion;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ReportedOpinionService {
    private final BusinessProfileRepository businessProfileRepository;
    private final ServiceOpinionRepository serviceOpinionRepository;
    private final ReportedOpinionRepository reportedOpinionRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final EntityMapper entityMapper;

    public ReportedOpinionService(BusinessProfileRepository businessProfileRepository,
                                  ServiceOpinionRepository serviceOpinionRepository,
                                  ReportedOpinionRepository reportedOpinionRepository,
                                  ServiceProviderRepository serviceProviderRepository, EntityMapper entityMapper) {
        this.businessProfileRepository = businessProfileRepository;
        this.serviceOpinionRepository = serviceOpinionRepository;
        this.reportedOpinionRepository = reportedOpinionRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.entityMapper = entityMapper;
    }

    @Transactional
    public void reportOpinionToAdmin(ReportedOpinionRequest reportedOpinionRequest, Principal principal) {
        String email = principal.getName();

        ServiceProvider serviceProvider = serviceProviderRepository.findByOwnerEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Business not found for this owner email:" + email));

        ServiceOpinion serviceOpinion = serviceOpinionRepository.findById(reportedOpinionRequest.getServiceOpinionId())
                .orElseThrow(() -> new EntityNotFoundException("Opinion not found"));

        ReportedOpinion reportedOpinion = new ReportedOpinion();
        reportedOpinion.setServiceProvider(serviceProvider);
        reportedOpinion.setServiceOpinion(serviceOpinion);
        reportedOpinion.setStatus(ReportStatus.REPORTED);
        reportedOpinion.setReason(reportedOpinionRequest.getReason());

        reportedOpinionRepository.save(reportedOpinion);
    }

    public List<ReportedOpinionDTO> findReportedOpinions() {
        List<ReportedOpinion> reportedOpinionList = reportedOpinionRepository.findAll();

        return entityMapper.mapReportedOpinionListToReportedOpinionListDTO(reportedOpinionList);
    }

    public ReportedOpinionDetailsDTO findReportedOpinionDetails(Long reportedOpinionId) {
        ReportedOpinion reportedOpinion = reportedOpinionRepository.findById(reportedOpinionId).orElseThrow(() ->
                new EntityNotFoundException("Opinion not found"));

        return entityMapper.mapReportedOpinionDetailsToReportedOpinionDetailsDTO(reportedOpinion);
    }

    public void deleteReportAndReportedOpinion(Long reportedOpinionId) {
        ReportedOpinion reportedOpinion = reportedOpinionRepository.findById(reportedOpinionId).orElseThrow(() ->
                new EntityNotFoundException("Opinion not found"));

        reportedOpinionRepository.delete(reportedOpinion);
        serviceOpinionRepository.delete(reportedOpinion.getServiceOpinion());
    }

    public void deleteReport(Long reportedOpinionId) {
        ReportedOpinion reportedOpinion = reportedOpinionRepository.findById(reportedOpinionId).orElseThrow(() ->
                new EntityNotFoundException("Opinion not found"));

        reportedOpinionRepository.delete(reportedOpinion);
    }
}
