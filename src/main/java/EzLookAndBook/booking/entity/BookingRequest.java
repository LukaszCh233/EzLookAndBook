package EzLookAndBook.booking.entity;

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
    @NotNull
    private LocalDate appointmentDate;
    @NotNull
    private LocalTime appointmentHour;
}
