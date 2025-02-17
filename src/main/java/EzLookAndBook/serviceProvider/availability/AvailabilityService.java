package EzLookAndBook.serviceProvider.availability;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOption;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalTime;
import java.util.List;

@Service
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final ServiceOptionRepository serviceOptionRepository;
    private final ServiceOptionService serviceOptionService;
    private final EntityMapper entityMapper;

    public AvailabilityService(AvailabilityRepository availabilityRepository,
                               ServiceOptionRepository serviceOptionRepository,
                               ServiceOptionService serviceOptionService
            , EntityMapper entityMapper) {
        this.availabilityRepository = availabilityRepository;
        this.serviceOptionRepository = serviceOptionRepository;
        this.serviceOptionService = serviceOptionService;
        this.entityMapper = entityMapper;
    }

    public List<AvailabilityDTO> findAvailabilityDateOption(Long optionId) {
        List<Availability> availability = availabilityRepository.findByServiceOptionId(optionId);
        if (availability.isEmpty()) {
            throw new EntityNotFoundException("Option don't have availability date");
        }
        return entityMapper.mapAvailabilityListToAvailabilityListDTO(availability);
    }

    public void addAvailability(Long serviceOptionId, AvailabilityRequest availabilityRequest, Principal principal) {
        ServiceOption serviceOption = serviceOptionService.findServiceOption(serviceOptionId, principal);

        Availability availability = new Availability();
        availability.setAvailableDate(availabilityRequest.getAvailableDate());
        availability.setAvailableHours(availabilityRequest.getAvailableHours());
        availability.setServiceOption(serviceOption);

        availabilityRepository.save(availability);

        serviceOption.getAvailabilities().add(availability);

        serviceOptionRepository.save(serviceOption);
    }

    public void updateAvailabilityHour(Long serviceOptionId, AvailabilityRequest availabilityRequest,
                                       Principal principal) {
        ServiceOption serviceOption = serviceOptionService.findServiceOption(serviceOptionId, principal);

        Availability availability = serviceOption.getAvailabilities().stream()
                .filter(a -> a.getAvailableDate().equals(availabilityRequest.getAvailableDate()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Availability for the given date not found"));

        availability.setAvailableHours(availabilityRequest.getAvailableHours());

        availabilityRepository.save(availability);
    }

    public void deleteAvailabilityDate(Long serviceOptionId, Long availabilityId, Principal principal) {
        ServiceOption serviceOption = serviceOptionService.findServiceOption(serviceOptionId, principal);

        Availability availability = serviceOption.getAvailabilities().stream()
                .filter(a -> a.getId().equals(availabilityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Availability not found"));

        serviceOption.getAvailabilities().remove(availability);
        serviceOptionRepository.save(serviceOption);
        availabilityRepository.delete(availability);
    }

    public void deleteAvailabilityHour(Long serviceOptionId, Long availabilityId, LocalTime hour, Principal principal) {
        ServiceOption serviceOption = serviceOptionService.findServiceOption(serviceOptionId, principal);

        Availability availability = serviceOption.getAvailabilities().stream()
                .filter(a -> a.getId().equals(availabilityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Availability not found"));

        availability.getAvailableHours().removeIf(time -> time.equals(hour));

        availabilityRepository.save(availability);
    }
}
