package EzLookAndBook.serviceProvider.booking.input;

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
    private Long ServiceProviderId;
    private Long ServiceOptionId;
    private String number;
    @NotNull
    private LocalDate appointmentDate;
    @NotNull
    private LocalTime appointmentHour;
}
