package EzLookAndBook.serviceProvider.serviceOption;

import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ServiceOptionService {
    private final ServiceOptionRepository serviceOptionRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final OwnerRepository ownerRepository;
    private final EntityMapper entityMapper;

    public ServiceOptionService(ServiceOptionRepository serviceOptionRepository,
                                ServiceProviderRepository serviceProviderRepository,
                                OwnerRepository ownerRepository, EntityMapper entityMapper) {
        this.serviceOptionRepository = serviceOptionRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.ownerRepository = ownerRepository;
        this.entityMapper = entityMapper;
    }

    @Transactional
    public void addServiceOption(ServiceOptionRequest serviceOptionRequest, Principal principal) {
        String email = principal.getName();

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        ServiceProvider serviceProvider = serviceProviderRepository.findByOwnerId(owner.getId()).orElseThrow(() ->
                new EntityNotFoundException("Service provider not found"));

        ServiceOption serviceOption = new ServiceOption();
        serviceOption.setName(serviceOptionRequest.getName());
        serviceOption.setDescription(serviceOptionRequest.getDescription());
        serviceOption.setPrice(serviceOptionRequest.getPrice());
        serviceOption.setServiceProvider(serviceProvider);
        serviceProvider.getServiceOptions().add(serviceOption);

        serviceProviderRepository.save(serviceProvider);
    }

    public void updateServiceOption(Long serviceOptionId, ServiceOptionRequest serviceOptionRequest,
                                    Principal principal) {
        ServiceOption serviceOptionToUpdate = findServiceOption(serviceOptionId, principal);

        serviceOptionToUpdate.setName(serviceOptionRequest.getName());
        serviceOptionToUpdate.setDescription(serviceOptionRequest.getDescription());
        serviceOptionToUpdate.setPrice(serviceOptionRequest.getPrice());

        serviceOptionRepository.save(serviceOptionToUpdate);
    }

    public void deleteServiceOption(Long serviceOptionId, Principal principal) {
        ServiceOption serviceOption = findServiceOption(serviceOptionId, principal);

        serviceOptionRepository.delete(serviceOption);
    }

    public List<ServiceOptionDTO> findAllServiceOption(Long serviceProviderId) {
        List<ServiceOption> serviceOptionList = serviceOptionRepository.findByServiceProviderId(serviceProviderId);
        if (serviceOptionList.isEmpty()) {
            throw new EntityNotFoundException("List is empty");
        }
        return entityMapper.mapServiceOptionListToServiceOptionListDTO(serviceOptionList);
    }

    public ServiceOption findServiceOption(Long serviceOptionId, Principal principal) {
        String email = principal.getName();

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        ServiceProvider serviceProvider = serviceProviderRepository.findByOwnerId(owner.getId()).orElseThrow(() ->
                new EntityNotFoundException("Service provider not found"));

        return serviceOptionRepository.findByServiceProviderIdAndId(
                serviceProvider.getId(), serviceOptionId).orElseThrow(() ->
                new EntityNotFoundException("Service option not found"));
    }
}
