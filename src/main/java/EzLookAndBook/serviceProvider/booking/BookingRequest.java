package EzLookAndBook.serviceProvider.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookingRequest {
    @NotNull(message = "ServiceProviderId cannot be null")
    private Long serviceProviderId;
    @NotNull(message = "ServiceOptionId cannot be null")
    private Long serviceOptionId;
    @NotBlank(message = "Number cannot be blank")
    private String number;
    @NotNull(message = "Appointment date cannot be null")
    private LocalDate appointmentDate;
    @NotNull(message = "Appointment hour cannot be null")
    private LocalTime appointmentHour;
}
