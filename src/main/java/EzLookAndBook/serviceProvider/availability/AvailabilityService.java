package EzLookAndBook.serviceProvider.availability;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.availability.AvailabilityDTO;
import EzLookAndBook.serviceProvider.availability.Availability;
import EzLookAndBook.serviceProvider.availability.AvailabilityRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final ServiceOptionRepository serviceOptionRepository;
    private final EntityMapper entityMapper;

    public AvailabilityService(AvailabilityRepository availabilityRepository, ServiceOptionRepository serviceOptionRepository
            , EntityMapper entityMapper) {
        this.availabilityRepository = availabilityRepository;
        this.serviceOptionRepository = serviceOptionRepository;
        this.entityMapper = entityMapper;
    }

    public AvailabilityDTO findAvailabilityDateOption(Long optionId) {
        Availability availability = availabilityRepository.findByServiceOptionId(optionId).orElseThrow(() ->
                new EntityNotFoundException("Option don't have availability date"));

        return entityMapper.mapAvailabilityToAvailabilityDTO(availability);
    }
}
