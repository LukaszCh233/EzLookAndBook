package EzLookAndBook.serviceProvider.service;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.dto.AvailabilityDTO;
import EzLookAndBook.serviceProvider.dto.ServiceOptionDTO;
import EzLookAndBook.serviceProvider.entity.Availability;
import EzLookAndBook.serviceProvider.entity.ServiceOption;
import EzLookAndBook.serviceProvider.entity.ServiceProvider;
import EzLookAndBook.serviceProvider.repository.AvailabilityRepository;
import EzLookAndBook.serviceProvider.repository.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.repository.ServiceProviderRepository;
import EzLookAndBook.user.entity.Owner;
import EzLookAndBook.user.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalTime;
import java.util.List;

@Service
public class ServiceOptionService {
    private final ServiceOptionRepository serviceOptionRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final OwnerRepository ownerRepository;
    private final AvailabilityRepository availabilityRepository;
    private final EntityMapper entityMapper;

    public ServiceOptionService(ServiceOptionRepository serviceOptionRepository,
                                ServiceProviderRepository serviceProviderRepository,
                                OwnerRepository ownerRepository, AvailabilityRepository availabilityRepository,
                                EntityMapper entityMapper) {
        this.serviceOptionRepository = serviceOptionRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.ownerRepository = ownerRepository;
        this.availabilityRepository = availabilityRepository;
        this.entityMapper = entityMapper;
    }

    public void addServiceOption(ServiceOptionDTO serviceOptionDTO, Principal principal) {
        String email = principal.getName();

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        ServiceProvider serviceProvider = serviceProviderRepository.findByOwnerId(owner.getId()).orElseThrow(() ->
                new EntityNotFoundException("Service provider not found"));

        ServiceOption serviceOption = new ServiceOption();
        serviceOption.setName(serviceOptionDTO.name());
        serviceOption.setDescription(serviceOptionDTO.description());
        serviceOption.setPrice(serviceOptionDTO.price());
        serviceOption.setServiceProvider(serviceProvider);
        serviceProvider.getServiceOptions().add(serviceOption);

        serviceProviderRepository.save(serviceProvider);
    }

    public void addAvailability(Long serviceOptionId, AvailabilityDTO availabilityDTO, Principal principal) {
        ServiceOption serviceOption = findServiceOption(serviceOptionId, principal);

        Availability availability = new Availability();
        availability.setAvailableDate(availabilityDTO.availableDate());
        availability.setAvailableHours(availabilityDTO.availableHours());
        availability.setServiceOption(serviceOption);

        availabilityRepository.save(availability);

        serviceOption.getAvailabilities().add(availability);
        serviceOptionRepository.save(serviceOption);
    }

    public void updateServiceOption(Long serviceOptionId, ServiceOptionDTO serviceOption, Principal principal) {
        ServiceOption serviceOptionToUpdate = findServiceOption(serviceOptionId, principal);

        serviceOptionToUpdate.setName(serviceOption.name());
        serviceOptionToUpdate.setDescription(serviceOption.description());
        serviceOptionToUpdate.setPrice(serviceOption.price());

        serviceOptionRepository.save(serviceOptionToUpdate);
    }

    public void updateAvailabilityHour(Long serviceOptionId, AvailabilityDTO availabilityDTO, Principal principal) {
        ServiceOption serviceOption = findServiceOption(serviceOptionId, principal);

        Availability availability = serviceOption.getAvailabilities().stream()
                .filter(a -> a.getAvailableDate().equals(availabilityDTO.availableDate()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Availability for the given date not found"));

        availability.setAvailableHours(availabilityDTO.availableHours());

        availabilityRepository.save(availability);
    }

    public void deleteServiceOption(Long serviceOptionId, Principal principal) {
        ServiceOption serviceOption = findServiceOption(serviceOptionId, principal);

        serviceOptionRepository.delete(serviceOption);
    }

    public void deleteAvailabilityDate(Long serviceOptionId, Long availabilityId, Principal principal) {
        ServiceOption serviceOption = findServiceOption(serviceOptionId, principal);

        Availability availability = serviceOption.getAvailabilities().stream()
                .filter(a -> a.getId().equals(availabilityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Availability not found"));

        serviceOption.getAvailabilities().remove(availability);
        serviceOptionRepository.save(serviceOption);
        availabilityRepository.delete(availability);
    }

    public void deleteAvailabilityHour(Long serviceOptionId, Long availabilityId, LocalTime hour, Principal principal) {
        ServiceOption serviceOption = findServiceOption(serviceOptionId, principal);

        Availability availability = serviceOption.getAvailabilities().stream()
                .filter(a -> a.getId().equals(availabilityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Availability not found"));

        availability.getAvailableHours().removeIf(time -> time.equals(hour));

        availabilityRepository.save(availability);
    }

    public List<ServiceOptionDTO> findAllServiceOption(Long serviceProviderId) {
        List<ServiceOption> serviceOptionList = serviceOptionRepository.findByServiceProviderId(serviceProviderId);
        if (serviceOptionList.isEmpty()) {
            throw new EntityNotFoundException("List is empty");
        }
        return entityMapper.mapServiceOptionListToServiceOptionListDTO(serviceOptionList);
    }

    private ServiceOption findServiceOption(Long serviceOptionId, Principal principal) {
        String email = principal.getName();

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        ServiceProvider serviceProvider = serviceProviderRepository.findByOwnerId(owner.getId()).orElseThrow(() ->
                new EntityNotFoundException("not found service provider"));

        return serviceOptionRepository.findByServiceProviderIdAndId(
                serviceProvider.getId(), serviceOptionId).orElseThrow(() -> new EntityNotFoundException("co"));
    }
}
