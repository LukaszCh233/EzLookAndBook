package EzLookAndBook.booking.entity;

import EzLookAndBook.booking.Status;
import EzLookAndBook.serviceProvider.entity.ServiceOption;
import EzLookAndBook.serviceProvider.entity.ServiceProvider;
import EzLookAndBook.user.entity.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceProvider serviceProvider;

    @ManyToOne
    @JoinColumn(name = "serviceOption_id", nullable = false)
    private ServiceOption serviceOption;

    @NotNull
    private LocalDate appointmentDate;
    @NotNull
    private LocalTime appointmentHour;
    private Status status;

}
