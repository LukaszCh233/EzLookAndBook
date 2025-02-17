package EzLookAndBook.serviceProvider.availability;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityRequest {
    @NotNull(message = "Date cannot be null")
    private LocalDate availableDate;
    @NotEmpty(message = "Available hours list cannot be empty")
    private List<LocalTime> availableHours;
}
