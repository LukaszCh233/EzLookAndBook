package EzLookAndBook.serviceProvider.serviceOpinion;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.report.ReportStatus;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfile;
import EzLookAndBook.serviceProvider.report.ReportedOpinionDTO;
import EzLookAndBook.serviceProvider.report.ReportedOpinionDetailsDTO;
import EzLookAndBook.serviceProvider.report.ReportOpinionRequest;
import EzLookAndBook.serviceProvider.report.ReportedOpinion;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileRepository;
import EzLookAndBook.serviceProvider.report.ReportedOpinionRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.account.owner.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceOpinionService {
    private final ServiceOpinionRepository serviceOpinionRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final ClientRepository clientRepository;
    private final ReportedOpinionRepository reportedOpinionRepository;
    private final OwnerRepository ownerRepository;
    private final BusinessProfileRepository businessProfileRepository;
    private final EntityMapper entityMapper;

    public ServiceOpinionService(ServiceOpinionRepository serviceOpinionRepository,
                                 ServiceProviderRepository serviceProviderRepository,
                                 ClientRepository clientRepository, ReportedOpinionRepository reportedOpinionRepository,
                                 OwnerRepository ownerRepository, BusinessProfileRepository businessProfileRepository,
                                 EntityMapper entityMapper) {
        this.serviceOpinionRepository = serviceOpinionRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.clientRepository = clientRepository;
        this.reportedOpinionRepository = reportedOpinionRepository;
        this.ownerRepository = ownerRepository;
        this.businessProfileRepository = businessProfileRepository;
        this.entityMapper = entityMapper;
    }

    public void addOpinion(Long serviceProviderId, OpinionRequest opinionRequest, Principal principal) {
        String email = principal.getName();

        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId).
                orElseThrow(() -> new EntityNotFoundException("Not found service provider"));

        Client client = clientRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Not found client"));

        ServiceOpinion serviceOpinion = new ServiceOpinion();
        serviceOpinion.setServiceProvider(serviceProvider);
        serviceOpinion.setUserId(client.getId());
        serviceOpinion.setUserName(client.getName());
        serviceOpinion.setLocalDateTime(LocalDateTime.now());
        serviceOpinion.setRating(opinionRequest.getRating());
        serviceOpinion.setOpinion(opinionRequest.getOpinion());

        serviceOpinionRepository.save(serviceOpinion);
    }

    public List<ServiceOpinionDTO> findServiceProviderOpinionsById(Long serviceProviderId) {
        List<ServiceOpinion> serviceOpinionList = serviceOpinionRepository.findByServiceProviderId(serviceProviderId);
        if (serviceOpinionList.isEmpty()) {
            throw new EntityNotFoundException("There are no opinions");
        }
        return entityMapper.mapServiceOpinionListToServiceOpinionListDTO(serviceOpinionList);
    }

    public ServiceOpinionDTO findServiceOpinionById(Long serviceOpinionId) {
        ServiceOpinion serviceOpinion = serviceOpinionRepository.findById(serviceOpinionId).orElseThrow(() ->
                new EntityNotFoundException("not found opinion"));

        return entityMapper.mapServiceOpinionToServiceOpinionDTO(serviceOpinion);
    }

    public void deleteOpinionByIdForClient(Long serviceOpinionId, Principal principal) {
        String username = principal.getName();
        Client client = clientRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("Not found client"));

        ServiceOpinion serviceOpinion = serviceOpinionRepository.findById(serviceOpinionId).orElseThrow(() ->
                new EntityNotFoundException("Not found opinion"));
        if (!serviceOpinion.getUserId().equals(client.getId())) {
            throw new UnsupportedOperationException("Client is not authorized to delete this opinion");
        }
        serviceOpinionRepository.delete(serviceOpinion);
    }

    public void deleteOpinionById(Long id) {
        ServiceOpinion serviceOpinion = serviceOpinionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found opinion"));

        serviceOpinionRepository.delete(serviceOpinion);
    }

    public void reportOpinionToAdmin(ReportOpinionRequest reportOpinionRequest, Principal principal) {
        String email = principal.getName();

        BusinessProfile businessProfile = businessProfileRepository.findByOwnerEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Business not found for this owner email:" + email));

        ServiceOpinion serviceOpinion = serviceOpinionRepository.findById(reportOpinionRequest.getServiceOpinionId())
                .orElseThrow(() -> new EntityNotFoundException("Opinion not found"));

        ReportedOpinion reportedOpinion = new ReportedOpinion();
        reportedOpinion.setBusinessProfile(businessProfile);
        reportedOpinion.setServiceOpinion(serviceOpinion);
        reportedOpinion.setStatus(ReportStatus.REPORTED);
        reportedOpinion.setReason(reportedOpinion.getReason());

        reportedOpinionRepository.save(reportedOpinion);
    }

    public List<ReportedOpinionDTO> findReportedOpinions() {
        List<ReportedOpinion> reportedOpinionList = reportedOpinionRepository.findAll();

        return entityMapper.mapReportedOpinionListToReportedOpinionListDTO(reportedOpinionList);
    }

    public ReportedOpinionDetailsDTO findReportedOpinionDetails(Long reportedOpinionId) {
        ReportedOpinion reportedOpinion = reportedOpinionRepository.findById(reportedOpinionId).orElseThrow(() ->
                new EntityNotFoundException("Not found opinion"));

        return entityMapper.mapReportedOpinionDetailsToReportedOpinionDetailsDTO(reportedOpinion);
    }

    public void deleteReportAndReportedOpinion(Long reportedOpinionId) {
        ReportedOpinion reportedOpinion = reportedOpinionRepository.findById(reportedOpinionId).orElseThrow(() ->
                new EntityNotFoundException("Not found opinion"));

        serviceOpinionRepository.delete(reportedOpinion.getServiceOpinion());
        reportedOpinionRepository.delete(reportedOpinion);
    }

    public void deleteReport(Long reportedOpinionId) {
        ReportedOpinion reportedOpinion = reportedOpinionRepository.findById(reportedOpinionId).orElseThrow(() ->
                new EntityNotFoundException("Not found opinion"));

        reportedOpinionRepository.delete(reportedOpinion);
    }
}
