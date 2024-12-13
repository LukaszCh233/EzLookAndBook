package EzLookAndBook.serviceProvider.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record AvailabilityDTO(LocalDateTime availableDate, List<LocalTime> availableHours) {
}
