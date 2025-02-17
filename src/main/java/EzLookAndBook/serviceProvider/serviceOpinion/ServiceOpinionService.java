package EzLookAndBook.serviceProvider.serviceOpinion;

import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
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
    private final EntityMapper entityMapper;

    public ServiceOpinionService(ServiceOpinionRepository serviceOpinionRepository,
                                 ServiceProviderRepository serviceProviderRepository,
                                 ClientRepository clientRepository, EntityMapper entityMapper) {
        this.serviceOpinionRepository = serviceOpinionRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.clientRepository = clientRepository;
        this.entityMapper = entityMapper;
    }

    public void addOpinion(Long serviceProviderId, OpinionRequest opinionRequest, Principal principal) {
        String email = principal.getName();

        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId).
                orElseThrow(() -> new EntityNotFoundException("Service provider not found"));

        Client client = clientRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Client not found"));

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
                new EntityNotFoundException("Opinion not found"));

        return entityMapper.mapServiceOpinionToServiceOpinionDTO(serviceOpinion);
    }

    public void deleteOpinionByIdForClient(Long serviceOpinionId, Principal principal) {
        String username = principal.getName();
        Client client = clientRepository.findByEmail(username).orElseThrow(() ->
                new EntityNotFoundException("Client not found"));

        ServiceOpinion serviceOpinion = serviceOpinionRepository.findById(serviceOpinionId).orElseThrow(() ->
                new EntityNotFoundException("Opinion not found"));
        if (!serviceOpinion.getUserId().equals(client.getId())) {
            throw new UnsupportedOperationException("Client is not authorized to delete this opinion");
        }
        serviceOpinionRepository.delete(serviceOpinion);
    }

    public void deleteOpinionById(Long id) {
        ServiceOpinion serviceOpinion = serviceOpinionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Opinion not found"));

        serviceOpinionRepository.delete(serviceOpinion);
    }
}
