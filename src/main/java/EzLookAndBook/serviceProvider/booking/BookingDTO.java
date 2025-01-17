package EzLookAndBook.serviceProvider.booking;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingDTO(String name, String number, String email, String serviceOption, LocalDate date,
                         LocalTime hour) {
}
