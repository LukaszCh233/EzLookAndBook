package EzLookAndBook.serviceProvider.availability;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AvailabilityDTO(String serviceOptionName, LocalDate availableDate, List<LocalTime> availableHours) {
}
