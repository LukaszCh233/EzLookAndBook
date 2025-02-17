package EzLookAndBook.serviceProvider.serviceProvider;

import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.mapper.EntityMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ServiceProviderService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final ClientRepository clientRepository;
    private final OwnerRepository ownerRepository;
    private final EntityMapper entityMapper;

    public ServiceProviderService(ServiceProviderRepository serviceProviderRepository, ClientRepository clientRepository, OwnerRepository ownerRepository, EntityMapper entityMapper) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.clientRepository = clientRepository;
        this.ownerRepository = ownerRepository;
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
        List<ServiceProvider> serviceProviderList;
        Client client = findClientByPrincipal(principal);

        if (client != null && client.isEnabled() && !client.getCity().equalsIgnoreCase("empty")) {
            serviceProviderList = serviceProviderRepository.findByServiceCategoryIdAndCityIgnoreCase(categoryId,
                    client.getCity());
        } else {
            serviceProviderList = serviceProviderRepository.findByServiceCategoryId(categoryId);

            if (serviceProviderList.isEmpty()) {
                throw new EntityNotFoundException("Not found service of this category");
            }
        }
        return entityMapper.mapServiceProviderListToServiceProviderListDTO(serviceProviderList);
    }

    public List<ServiceProviderDTO> findServiceProviderByCategoryName(String categoryName, Principal principal) {
        List<ServiceProvider> serviceProviderList;
        Client client = findClientByPrincipal(principal);

        if (client != null && client.isEnabled() && !client.getCity().equalsIgnoreCase("empty")) {
            serviceProviderList = serviceProviderRepository.findByServiceCategoryNameAndCityIgnoreCase
                    (categoryName, client.getCity());
        } else {
            serviceProviderList = serviceProviderRepository.findByServiceCategoryName(categoryName);
        }
        if (serviceProviderList.isEmpty()) {
            throw new EntityNotFoundException("Not found service of this category");
        }
        return entityMapper.mapServiceProviderListToServiceProviderListDTO(serviceProviderList);
    }

    public ServiceProviderDTO findServiceProviderById(Long id, Principal principal) {
        ServiceProvider serviceProvider;
        Client client = findClientByPrincipal(principal);

        if (client != null && client.isEnabled() && !client.getCity().equalsIgnoreCase("empty")) {
            serviceProvider = serviceProviderRepository.findByIdAndCityIgnoreCase(id, client.getCity()).orElseThrow(()
                    -> new EntityNotFoundException("Service not found"));
        } else {
            serviceProvider = serviceProviderRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Service not found"));
        }
        return entityMapper.mapServiceProviderToServiceProviderDTO(serviceProvider);
    }

    public ServiceProviderDTO findServiceProviderByPrincipal(Principal principal) {
        String email = principal.getName();

        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Owner not found"));

        ServiceProvider serviceProvider = serviceProviderRepository.findByOwnerId(owner.getId()).orElseThrow(() ->
                new EntityNotFoundException("Service not found"));

        return entityMapper.mapServiceProviderToServiceProviderDTO(serviceProvider);
    }

    public List<ServiceProviderDTO> findServiceProviderByServiceOption(String optionName, Principal principal) {
        List<ServiceProvider> serviceProviderList;
        Client client = findClientByPrincipal(principal);

        if (client != null && client.isEnabled() && !client.getCity().equalsIgnoreCase("empty")) {
            serviceProviderList = serviceProviderRepository.findByServiceOptionNameAndCityIgnoreCase(optionName,
                    client.getCity());
        } else {
            serviceProviderList = serviceProviderRepository.findByServiceOptionsName(optionName);
        }
        if (serviceProviderList.isEmpty()) {
            throw new EntityNotFoundException("Not found service with that option");
        }
        return entityMapper.mapServiceProviderListToServiceProviderListDTO(serviceProviderList);
    }

    private Client findClientByPrincipal(Principal principal) {
        String email = principal.getName();

        return clientRepository.findByEmail(email).orElse(null);
    }
}
