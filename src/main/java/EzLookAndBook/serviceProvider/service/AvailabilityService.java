package EzLookAndBook.serviceProvider.service;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.dto.AvailabilityDTO;
import EzLookAndBook.serviceProvider.entity.Availability;
import EzLookAndBook.serviceProvider.repository.AvailabilityRepository;
import EzLookAndBook.serviceProvider.repository.ServiceOptionRepository;
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
