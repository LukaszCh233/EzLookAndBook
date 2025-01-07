package EzLookAndBook.serviceProvider.service;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.dto.ServiceProviderDTO;
import EzLookAndBook.serviceProvider.entity.ServiceProvider;
import EzLookAndBook.serviceProvider.repository.ServiceCategoryRepository;
import EzLookAndBook.serviceProvider.repository.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.repository.ServiceProviderRepository;
import EzLookAndBook.user.entity.Client;
import EzLookAndBook.user.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ServiceProviderService {
    private final ServiceOptionRepository serviceOptionRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final ClientRepository clientRepository;
    private final EntityMapper entityMapper;

    public ServiceProviderService(ServiceOptionRepository serviceOptionRepository,
                                  ServiceCategoryRepository serviceCategoryRepository,
                                  ServiceProviderRepository serviceProviderRepository, ClientRepository clientRepository,
                                  EntityMapper entityMapper) {
        this.serviceOptionRepository = serviceOptionRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.clientRepository = clientRepository;
        this.entityMapper = entityMapper;
    }

    public void setCityForClient(String city, Principal principal) {
        Client client = findClientByPrincipal(principal);

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be empty");
        }
        client.setCity(city);

        clientRepository.save(client);
    }

    public List<ServiceProviderDTO> findServiceProviderByCategoryId(Long categoryId, Principal principal) {
        Client client = findClientByPrincipal(principal);

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findByServiceCategoryIdAndCityIgnoreCase(categoryId,
                client.getCity());
        if (serviceProviderList.isEmpty()) {
            throw new EntityNotFoundException("Not found service of this category");
        }
        return entityMapper.mapServiceProviderListToServiceProviderListDTO(serviceProviderList);
    }

    public List<ServiceProviderDTO> findServiceProviderByCategoryName(String categoryName, Principal principal) {
        Client client = findClientByPrincipal(principal);

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findByServiceCategoryNameAndCityIgnoreCase(categoryName,
                client.getCity());
        if (serviceProviderList.isEmpty()) {
            throw new EntityNotFoundException("Not found service of this category");
        }
        return entityMapper.mapServiceProviderListToServiceProviderListDTO(serviceProviderList);
    }

    public ServiceProviderDTO findServiceProviderById(Long id, Principal principal) {
        Client client = findClientByPrincipal(principal);

        ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found service"));

        if (!serviceProvider.getCity().equalsIgnoreCase(client.getCity())) {
            throw new EntityNotFoundException("Service not found in the specified city");
        }

        return entityMapper.mapServiceProviderToServiceProviderDTO(serviceProvider);
    }

    public List<ServiceProviderDTO> findServiceProviderByServiceOption(String optionName, Principal principal) {
        Client client = findClientByPrincipal(principal);

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findByServiceOptionNameAndCityIgnoreCase
                (optionName, client.getCity());
        if (serviceProviderList.isEmpty()) {
            throw new EntityNotFoundException("Not found service with that option");
        }
        return entityMapper.mapServiceProviderListToServiceProviderListDTO(serviceProviderList);
    }

    private Client findClientByPrincipal(Principal principal) {
        String email = principal.getName();

        return clientRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
