package EzLookAndBook.serviceProvider.service;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.dto.ServiceOpinionDTO;
import EzLookAndBook.serviceProvider.entity.OpinionRequest;
import EzLookAndBook.serviceProvider.entity.ServiceOpinion;
import EzLookAndBook.serviceProvider.entity.ServiceProvider;
import EzLookAndBook.serviceProvider.repository.ServiceCategoryRepository;
import EzLookAndBook.serviceProvider.repository.ServiceOpinionRepository;
import EzLookAndBook.serviceProvider.repository.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.repository.ServiceProviderRepository;
import EzLookAndBook.user.entity.Client;
import EzLookAndBook.user.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceOpinionService {
    private final ServiceOptionRepository serviceOptionRepository;
    private final ServiceOpinionRepository serviceOpinionRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final ClientRepository clientRepository;
    private final EntityMapper entityMapper;

    public ServiceOpinionService(ServiceOptionRepository serviceOptionRepository,
                                 ServiceOpinionRepository serviceOpinionRepository,
                                 ServiceCategoryRepository serviceCategoryRepository,
                                 ServiceProviderRepository serviceProviderRepository, ClientRepository clientRepository,
                                 EntityMapper entityMapper) {
        this.serviceOptionRepository = serviceOptionRepository;
        this.serviceOpinionRepository = serviceOpinionRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.clientRepository = clientRepository;
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

    public void deleteOpinionByIdForClient(Long id, Principal principal) {
        String username = principal.getName();
        Client client = clientRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("Not found client"));

        ServiceOpinion serviceOpinion = serviceOpinionRepository.findById(id).orElseThrow(() ->
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
}
